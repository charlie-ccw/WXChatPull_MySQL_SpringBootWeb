# 目录
<!-- TOC -->

- [目录](#目录)
- [介绍](#介绍)
- [搭建详解](#搭建详解)
  - [wework](#wework)
  - [baiduword](#baiduword)
  - [xunfeiwork](#xunfeiwork)
  - [MySQL](#mysql)
  - [config](#config)
  - [util](#util)
  - [controller](#controller)
- [运行方法](#运行方法)
- [报错与补充说明](#报错与补充说明)
- [效果展示](#效果展示)
  - [百度与讯飞的语音转文字](#百度与讯飞的语音转文字)
    - [音频1：](#音频1)
  - [模型预测结果](#模型预测结果)
    - [输入1：](#输入1)
    - [输入2：](#输入2)
    - [输入3：](#输入3)
    - [输入4：](#输入4)

<!-- /TOC -->

# 介绍
该项目基于 **SpringBoot** 框架搭建，通过Http请求调用各接口并可与MySQL连接进行存储。

如在本机上运行，只需要在程序搭建完成并运行后输入网址 http://127.0.0.1:8080/Labels 即可调用接口进行企业微信会话拉取，实体标注模型预测与会话结果数据库存档

# 搭建详解
项目的开发环境为***Ubuntu-22.04.1***，`src\main\java\com\paradigm`目录下共有7个子目录，接下来分别介绍：

## wework
企业微信提供的官方接口可在`src\main\java\com\tencent\wework\Finance.java`中查看，具体使用文档可登录 https://developer.work.weixin.qq.com/document/path/91360 查看。调用前需要将`lib`文件夹中的`libWeWorkFinanceSdk_Java.so`配置文件放入linux环境中的`/usr/lib64`地址内。同时将`com\paradigm\wework\WeChatDemo.java`文件中的`corpid` `secret`调整为公司的会话存档申请，完成后前往 http://web.chacuo.net/netrsakeypair 中生成RSA密钥对，选择 ***2048bit*** 密钥位数与 ***PKCS#1*** 密钥格式，获取到密钥对后将其保存起来并修改java文件中的`priKey`为生成的私钥并前往 https://work.weixin.qq.com/ 选择企业登录后进入**管理工具**，选择**会话内容存档**设置**公钥**与其他权限，上述的`secret`也可在此处查看。

完成上述的基础配置搭建后方可调用接口实现会话的拉取，默认可拉取**3天内**的会话，其中部分重要的会话类型的测试已经通过，具体请看doc文件夹内的`企业微信会话拉取测试.xlsx`文件查看。

## baiduword
该目录负责调用百度提供的智能语音识别JAVA API，使用前需在官网 https://ai.baidu.com/tech/speech 注册登录并申请使用资格，申请成功后需要将子目录下的Java文件中的`APP_ID`, `API_KEY`, `SECRET_KEY`调整为自己的申请方可调用接口，可在登陆后进入**应用列表**查看。接口的具体使用文档可进入**技术文档**查找，选择 ***语音识别 -> 短语音识别标准版 -> REST-API-JavaSDK*** 进行查看 https://ai.baidu.com/ai-doc/SPEECH/ok4o0bk7z

## xunfeiwork
该目录可调用科大讯飞的智能语音识别功能中的语音听写JAVA API，同百度接口一样，使用前需前往 https://www.xfyun.cn/services/voicedictation 申请使用资格并创建新应用，完成后可前往**我的应用**中找到创建的应用并查看申请到的`APPID`,获取到APPID后将`XunFeiVocalToTextDemo.java`中**RecognizePcmfileByte**方法下的APPID换成自己创建的。
申请完成后需要将`lib`文件夹中的`libmsc64.so`配置文件放入linux环境中的`/usr/lib64`地址内，同时将`MSC.jar`导入项目。**注意：由于文件格式原因，请自行安装ffmpeg工具进行媒体文件格式转化，原因在controller的搭建详解第4条中**

完成上述的基础配置搭建后方可调用接口实现科大讯飞的语音听写接口调用，具体的使用文档可前往 https://www.xfyun.cn/doc/asr/voicedictation/Java-SDK.html 查阅。

## MySQL
使用MySQL请提前在官网 https://www.mysql.com/downloads/ 进行下载安装。MySQL有许多可视化软件支持，例如**Navicat**，如有需要可自行下载使用。将MySQL安装好后，使用管理员权限打开**CMD**输入`net start mysql`即可打开MySQL的服务。

该子目录中有相互对应的**Mapper**接口与**domain**数据类，Mapper负责调用SQL操作语句，但在使用前请根据domain中的Java类在MySQL库中创建相对应的表进行存储与查询等操作。

将上述准备工作完成后，需打开`src\main\resources`目录，修改其下的`.yml`配置文件内容，修改 ***url*** 以连接到所使用的数据库，修改 ***username*** 与 ***password*** 以获取访问与操作权限。

## config
`config\RestTemplateConfig.java`是一个RestTemplate配置类，RestTemplatge是Spring中用于访问Rest服务的客户端，其提供了多种便捷访问远程Http服务的方法，配置完成后即可实现在控制层向服务器发送请求调用模型预测以获取实体识别的结果。

## util
调用企业微信的会话拉取接口后会得到数据类型为`ArrayList<String>`的结果，目录下`messageToken.java`的主要功能是将`String`类型的结果转换成与数据库中***message***表对应的`Message`domain对象以便存储。`messageToken.java`中针对不同的会话消息类型需要做相对应的操作以保证数据库存储正确的会话内容（当拉取的会话消息类型为**会话记录消息**时，每一条会话记录item会被存储在recorditem表中）。其中部分重要的会话类型的测试已经通过，具体请看doc文件夹内的`企业微信会话拉取测试.xlsx`文件查看。

服务器中模型预测的返回结果是`List<List<JSON>>`格式，具体返回样例可以查看报错与补充说明的第4条。目录下`label_personToken.java`的主要功能是将`List<JsonObject>`类型的输入转换成与数据库中**label**或**person**表对应的`Label`或`Person`domain对象以便存储。其中，**Label**与**msgid**相关联，而**Person**独立存储人物的信息内容，这两个表的查询方式会在**运行**的第6条说明。

## controller
`controller\LabelController.java`为本项目控制层，这里具体介绍其调用接口的流程并将上方所介绍的多个接口与MySQL数据库结合起来以加深项目理解：

1. 在发送GET请求访问**http://{ip_address}:8080/Labels**后，控制层会调用企业微信提供的会话拉取接口以获取从第`seq+1`开始往后的会话，最大拉取`limit`条会话，其中**seq**和**limit**可以在`wework\WeChatDemo.java`中修改，返回的数据类型为`ArrayList<String>`。

2. 成功拉取到会话信息后，调用`messageToken`类中的`getTokens`方法，根据每一条会话消息的`msgid`判断数据库是否已经存储过此条消息。如果没有在数据库内找到，则会识别会话信息的类别并作相对应的处理将其转化成`Message`domain类与MySQL中的message表对应以方便存储，返回的数据类型为`ArrayList<Message>`。在转化的过程中如果识别到消息类型有媒体文件时，会调用企业微信提供的媒体拉取接口以获取到完整的媒体文件，例如：图片，音频，视频等。这些媒体文件会被存储在`MediaResource/`文件夹中。

3. 完成转化后，与MySQL连接并存储到message表中。

4. 接下来识别会话消息的类型是否为音频，如果是音频则会调用百度或者讯飞的智能语音转文字接口将音频信息转为文字，转换的结果会存入音频会话消息的**content**参数中。由于微信拉取的音频媒体是`amr`格式文件，而讯飞不支持该文件格式，所以会在调用接口前通过命令行操纵`ffmpeg`工具将音频文件转化成`wav`格式，转化后的文件名为`test.wav`，具体的命令行可打开`xunfeiwork\ffmpegwork\AudioConverter.java`进行查看和修改，`amr`格式的原音频文件不会被覆盖。
   
5. 对会话消息的content进行提取，如果发现content有值，则会向模型服务器发送POST请求并设置`RequestBody`为`{sentence: content}`。在获取到结果后调用**util\label_personToken.java**中的**getPerson**与**getLabel**方法对结果进行格式转换并存储进数据库中对应的**person表**与**label表**
   

# 运行方法
1. 在linux环境下按照搭建详详解完成Spring项目的基础搭建
   
2. 启动MySQL服务
   ```
   net start mysql
   ```
3. 测试与数据库服务器的网络连接，能ping上即可
   
4. 搭建并运行paddle模型的服务器，搭建模型的方法参考 https://github.com/PaddlePaddle/PaddleNLP 。运行该模型只需要在模型下载后将本项目中`model\demo.py`文件放入模型的`PaddleNLP\`目录下，打开IDEA点击运行即可。

5. 如果在本机上运行项目，直接打开浏览器访问 http://127.0.0.1:8080/Labels 或通过postman模拟GET请求进行访问。
   
   如果是在服务器上运行项目，先测试与服务器的网络连接后再通过Get请求访问 `http://{ip_address}:8080/Labels`

6. 通过GET请求访问 `http://{ip_address}:8080/Labels/{msgid}` 可以查询对应会话的实体标注结果
   
   通过POST请求设置`RequestBody`为`{name：需要查询的人名}`并访问 `http://{ip_address}:8080/Labels` 可以查询对应人名的人物信息

# 报错与补充说明
1. Ubuntu无网络连接时，在**Terminal**输入`ip address`发现无ip地址并显示： `ens33: <BROADCAST,MULTICAST> mtu 1500 qdisc noop state DOWN group default qlen 1000`，可在terminal输入：
   
   ```
   sudo dhclient ens33
   ```
2. 本项目尝试调用**百度**与**讯飞**的智能语音识别功能，但是目前来说两者的效果都是一般，后期可以通过训练识别模型来提高效果，具体训练方法需查看百度与讯飞的**在线文档**
   
   百度文档： https://ai.baidu.com/ai-doc/SPEECH/0k3h17u76

   讯飞文档： https://www.xfyun.cn/doc/asr/voicedictation/train.html

3. 在调试阶段，如果选择清空**message**表进行重新拉取，请同时清空**label**表与**recorditem**表，不然会导致信息重复存储可能会报错。**person**表可以不用清空，在存储人物信息前，会先检测表中是否有该人名，如果有则只会更新信息但不会重复添加，如果没有则会创建新人物并存储，具体可以查看`util\label_personToken.java`

4. 样例1：
   ```
   [
      [{姓名=李湘, 年龄=88.7, 性别=男, 收入=50w, 组织=信通院, 职位=信通院研究员}, {姓名=王邓, 年龄=94.3.14, 性别=男, 职位=信通院研究员}], 
      [], 
      [{保险=平安医疗险}, {病症=重病}, {年龄=94.3.14}, {年龄=88.7}, {收入=50w}, {职位=信通院研究员}, {购买意向=强}, {性别=男}, {组织=信通院}]
   ]
   ```
   样例2：
   ```
   [
      [{姓名=王邓, 年龄=94.3.14, 性别=女, 收入=40w}], 
      [{情感倾向[正向，负向]=正向, 观点词=40w, 评价对象=收入}], 
      [{保险=平安医疗险}, {年龄=94.3.14}, {收入=40w}, {购买意向=强}, {性别=女}, {组织=美团PM}]
   ]
   ```

5. 在调用语音转文字接口时有可能会报错误码，其中讯飞报错误码的概率大于百度，常见的讯飞的错误码有：
   
   **10114** ：表示音频时间过长超过60s。

   **10008** ： 句柄错误，请求无效句柄，听写websocket接口，客户端调用问题，例如语音听写英文模式下，请求第一帧就发送了status=2。

   -----其他具体的错误码可以访问：-----

   百度： https://ai.baidu.com/ai-doc/SPEECH/Yk4o0bkop

   讯飞： https://www.xfyun.cn/doc/asr/voicedictation/API.html#%E9%94%99%E8%AF%AF%E7%A0%81


# 效果展示

## 百度与讯飞的语音转文字
不管调用的是百度还是讯飞的接口，在调用前都会将微信拉取的`amr`格式音频转换成`wav`格式，转换后的音频为**单声道**且抽样率设置成**16K**。具体的音频文件存放在`Vocal`文件夹中，可以对照下面的输出播放文件夹中对应的音频。总体来说效果不如微信自身的转文字，但是企业微信没这个接口。
### 音频1：
录音的原文本：
```
李湘，性别为男，88年7月出生，目前在信通院做研究员，他本人并不认可保险，但家中有亲戚得了重病，属于回避型客户，单位里一直有补充医疗，同时他小时候家里给买过年金，现在每年有3000块分红。有一个女儿今年4岁，妻子是一位教师，家庭总年收入大约50万，目前每个月有9千的房贷，他对父母医疗险感兴趣。下周一约夫妻双方一起聊一聊，他本人的体检结果未知。
```
百度结果：
```
李湘，性别为难88年七月出生，目前在信通院做研究员，他本人并不认可保险，但家中有亲戚得了重病，属于回避型客户，单位里一直有补充医疗，同时，他小时候家里给买过年金，现在每年有3000块分红，有一个女儿今年四岁，妻子是一位教师，家庭总年收入大约50万，目前每个月有9000的房贷，他对父母医疗险感兴趣，下周一约，夫妻双方一起聊一聊，他本人的体检结果位置。

[
   [{姓名=李湘, 年龄=88年七月, 性别=为难, 收入=50万, 职位=研究员}], 
   [], 
   [{年龄=88年七月}, {职位=研究员}, {收入=50万}, {性别=为难}, {病症=重病}, {组织=信通院}]
]
```
讯飞结果：
```
李湘性别为男，88年7月出生，目前在信通院做研究员，他本人并不认可保险，但家中有亲戚得了重病属于回避型，客户单位里一直有补充医疗，同时他小时候家里给买个年金，现在每年有3000块分红，有一个女儿今年4岁，妻子是一位教师家庭总年收入大约50万，目前每个月有9000的房贷，他对父母医疗险感兴趣，下周一曰，夫妻双方一起聊一聊，他本人的体检结果未知。

[
   [{姓名=李湘, 年龄=88年7月, 性别=男, 收入=50万, 职位=研究员}], 
   [], 
   [{组织=信通院}, {性别=男}, {年龄=88年7月}, {病症=重病}, {职位=研究员}]
]
```


## 模型预测结果
目前使用的**schema**：
```
schema1： [{'人名': ['年龄', '性别', '收入', '组织', '职位']}]
schema2： {'评价维度': ['观点词', '情感倾向[正向，负向]']}
schema3： {'购买意向', '保险', '药物', '病症', '手术', '年龄', '性别', '收入', '组织', '职位'}
```
目前使用了三个schema，每一条都需要让模型单独预测，因此一次的接口调用会让模型预测三次并一起返回结果
### 输入1：
陈定，女，94.3.14，美团PM，已买平安医疗险，公司给买10w重疾，单身无房贷，考虑婚前财产隔离，重疾和年金险意向强，有看过知乎小红书科普，收入40w，预算3w，体检正常，后天出方案。
```
结果：
[
   [{'姓名': '陈定', '年龄': '94.3.14', '性别': '女', '收入': '40w'}], 
   [{'评价对象': '收入', '观点词': '40w', '情感倾向[正向，负向]': '正向'}], 
   [{'年龄': '94.3.14'}, {'收入': '40w'}, {'组织': '美团PM'}, {'保险': '平安医疗险'}, {'性别': '女'}, {'购买意向': '强'}]
] 
```
### 输入2：
李湘，男，88.7，信通院研究员，不认可保险，但家中有亲戚重病，有感受，回避型，单位有补充医疗，小时候家里给买过年金，现在每年有3000分红。有女儿4岁，妻子教师，家庭年收入50w，房贷9k，对父母医疗险感兴趣。下周一约夫妻同聊，体检未知，建议带过去保单和体检报告。
``` 
结果：
[
   [{'姓名': '李湘', '年龄': '88.7', '性别': '男', '收入': '50w', '组织': '信通院', '职位': '信通院研究员'}], 
   [], 
   [{'年龄': '88.7'}, {'年龄': '4岁'}, {'收入': '50w'}, {'组织': '信通院'}, {'病症': '重病'}, {'职位': '信通院研究员'}, {'性别': '男'}]
]
```
### 输入3：
王武，男，联通测试，95年，对保险知识感兴趣，购买意向弱。单身无房贷，父母健康，公司有补充医疗，契机是同事重病，但对自己身体自信，略纠结。可从紧迫感入手。发链接慢慢影响。
```
结果：
[
   [{'姓名': '王武', '年龄': '95年', '性别': '男', '职位': '联通测试'}], 
   [{'评价对象': '购买意向', '观点词': '弱', '情感倾向[正向，负向]': '负向'}], 
   [{'年龄': '95年'}, {'病症': '重病'}, {'职位': '联通测试'}, {'性别': '男'}, {'购买意向': '弱'}]
]
```
### 输入4：
陈定，女，94年出生，生日是3月14，美团PM，已买平安医疗险，公司给买10w重疾，单身无房贷，考虑婚前财产隔离，重疾和年金险意向强，有看过知乎小红书科普，收入40w，预算3w，体检正常，后天出方案。李湘，男，88.7，信通院研究员，不认可保险，但家中有亲戚重病，有感受，回避型，单位有补充医疗，小时候家里给买过年金，现在每年有3000分红。有女儿4岁，妻子教师，家庭年收入50w，房贷9k，对父母医疗险感兴趣。下周一约夫妻同聊，体检未知，建议带过去保单和体检报告。
```
结果：
[
   [{'姓名': '李湘', '年龄': '88.7', '职位': '信通院研究员'}, {'姓名': '陈定', '年龄': '94年', '性别': '女', '收入': '40w', '职位': '信通院研究员'}], 
   [{'评价对象': '收入', '情感倾向[正向，负向]': '正向'}], 
   [{'年龄': '94年'}, {'职位': '信通院研究员'}, {'病症': '重病'}, {'保险': '平安医疗险'}, {'组织': '美团PM'}, {'组织': '信通院'}, {'购买意向': '强'}, {'收入': '40w'}, {'性别': '女'}]
   ]
```
**注意：当输入的文本具有多人物时，基础模型并不能将每个人物的具体属性信息一一对应上，有缺陷**
