import json
from paddlenlp import Taskflow
from flask import Flask, jsonify, Response, request

def personExchange(lst):
    results = []
    for i in lst:
        if '人名' in i:
            for j in i['人名']:
                result = {"姓名": j['text']}
                if 'relations' in j:
                    for key in j['relations']:
                        result.update({key: j['relations'][key][0]['text']})
                results.append(result)
        elif '评价维度' in i:
            for j in i['评价维度']:
                result = {"评价对象": j['text']}
                if 'relations' in j:
                    for key in j['relations']:
                        result.update({key: j['relations'][key][0]['text']})
                results.append(result)
        else:
            for key in i:
                for j in i[key]:
                    result = {key: j['text']}
                    results.append(result)
    return results


def main():
    schema = [{'人名': ['年龄', '性别', '收入', '组织', '职位']}]
    return Taskflow('information_extraction', schema=schema, position_prob=0.6)



app = Flask(__name__)


@app.route("/", methods=['GET', 'POST'])
def hello():
    s = []
    sentence = request.form['sentence']
    s.append(personExchange(ie(sentence)))

    schema = {'评价维度': ['观点词', '情感倾向[正向，负向]']}
    ie.set_schema(schema)
    s.append(personExchange(ie(sentence)))

    schema = {'购买意向', '保险', '药物', '病症', '手术', '年龄', '性别', '收入', '组织', '职位'}
    ie.set_schema(schema)
    s.append(personExchange(ie(sentence)))

    schema = [{'人名': ['年龄', '性别', '收入', '组织', '职位']}]
    ie.set_schema(schema)
    print(s)
    return s


if __name__ == "__main__":
    ie = main()
    app.run(host="0.0.0.0")
    app.config['JSON_AS_ASCII'] = False
