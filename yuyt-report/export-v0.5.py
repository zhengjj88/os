# -*- coding: utf-8 -*-

import sys
import csv
import cx_Oracle
import ConfigParser
import json
import codecs
import time
import datetime
import smtplib

from datetime import date
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage 

printHeader= True
config = ConfigParser.ConfigParser()
config.readfp(open("config.ini","rb"))
username = config.get("datasource","username")
password = config.get('datasource','password')
url = config.get('datasource','url')

queryHospital= config.get('sql','sql.query.hos')

sender = config.get('mail','sender')
receiver = config.get('mail','receiver')
subject = config.get('mail','subject')
smtpserver = config.get('mail','smtpserver')
mailusername = config.get('mail','mailusername')
mailpassword = config.get('mail','mailpassword')

msgRoot = MIMEMultipart('alternative')
msgRoot['Subject'] = subject

oneday = datetime.timedelta(days=1)
today = date.today()
yesterday = today - oneday
yesterday = yesterday.isoformat()
db=cx_Oracle.connect(username,password,url)
curs = db.cursor()
curs.execute(queryHospital)

hospitals = []
for row_data in curs:
    hospitals.append(row_data)

for hos in hospitals:
    filename = hos[0]
    hosCode = hos[1]
    querySql = hos[2]
    curs.execute(querySql,[hosCode])
    csv_file =filename+'-'+yesterday+'.csv'
    outputFile =open(csv_file,'wb')
    output = csv.writer(outputFile,dialect='excel')
    if printHeader:
        cols=[]
        for col in curs.description:
            cols.append(col[0])
        output.writerow(cols)

        for row_data in curs:
            output.writerow(row_data)
        outputFile.close()
    att = MIMEText(open(csv_file, 'rb').read(), 'base64', 'utf-8')
    att["Content-Type"] = 'application/octet-stream'
    att["Content-Disposition"] = 'attachment; filename='+csv_file.decode('gbk').encode('utf8')
    msgRoot.attach(att) 

db.close()

smtp = smtplib.SMTP()
smtp.connect(smtpserver)
smtp.login(mailusername, mailpassword)
smtp.sendmail(sender, receiver, msgRoot.as_string())
smtp.quit()




