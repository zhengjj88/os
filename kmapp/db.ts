import mysql = require("mysql");
import winston = require('winston');

var db = mysql.createPool({
  connectionLimit : 10,
  host            : 'localhost',
  user            : 'root',
  password        : '123456',
  database : 'KMAPPDB'
});

db.query('SELECT 1 + 1 AS solution', function(err, rows, fields) {
  if (err) throw err;

  console.log('The solution is: ', rows[0].solution);
});

export interface User {
  id: string;
  username: string;
  password: string;
  realname: string;
  createdate: Date;
  imsi: string;
  dept: string;
  subcompany: string;
  enable: string;
}

export interface PunchCard {
  id: string;
  username: string;
  location: string;
  dept: string;
  createdate: Date;
  state: string;
}

export interface StringArray {
  [index: string]: string;
}

class SqlBuilder{
  tableName: string;
  selector: StringArray;
  sql: string;
  insert = {
    values: []
  };
  page: Page;

  constructor(tableName: string, selector: StringArray, page:Page){
    this.tableName = tableName;
    this.selector = selector;
    this.page = page;
  }

  build() {
    var i = 0;
    var whereClause = " WHERE ";
    this.sql = "SELECT * FROM " + this.tableName;
    if(this.selector != null) {
      for( var name in this.selector) {
        if (i > 0){
          if(name == 'CREATE_DATE'){
            console.log('............................'+this.selector[name][0]+",.........."+this.selector[name][1]);
            whereClause = whereClause.concat (' and ').concat(name + ' between ? and ? ');
            this.insert.values.push(this.selector[name][0]); 
            this.insert.values.push(this.selector[name][1]); 
          }else{  
            whereClause = whereClause.concat(' and ').concat(name+ ' = ? ');
            this.insert.values.push(this.selector[name]);
          }
        }else {
          if(name == 'CREATE_DATE') {////////////////////////////////////////////////////////specify when query punch card by createdate
            whereClause = whereClause.concat(name + ' between ? and ? ');
            this.insert.values.push(this.selector[name][0]); 
            this.insert.values.push(this.selector[name][1]); 
          }else{  
            whereClause = whereClause.concat (name + ' = ? ');
            this.insert.values.push(this.selector[name]);
          }
        }
        i++;
      }

      this.sql = this.sql.concat(whereClause);
    }
    if(this.page != null ){
      this.sql = this.sql.concat(" limit ?, ? ");
      this.insert.values.push(Number(this.page.offset));
      this.insert.values.push(Number(this.page.rowCount));
    }
    console.log('query sql is ' + this.sql);
    console.log('inserts is ' + this.insert.values);
  }
}

export class Page {
  offset: number;
  rowCount: number; 

  constructor(pageNumber: number, pageSize: number){
    this.rowCount = pageSize;
    this.offset = this.rowCount * pageNumber;
    winston.info('offset = ' + this.offset + ', rowCount = ' + this.rowCount + ', pageNumber = ' + pageNumber);
  }
}

export function addUser(username: string, password: string, realname: string, createdate: Date, imsi: string, dept: string, subcompany: string, enable: string, callback: (user: User) => void){
  db.query('INSERT INTO USER SET ?', {USERNAME:username, PASSWORD:password, REAL_NAME:realname, CREATE_DATE:createdate, IMSI:imsi, DEPT:dept, SUB_COMPANY: subcompany, ENABLE: 'Y'}, function(err, result){
         if (err) throw err;
         console.log(result.insertId);
       });
};


export function findUsers(selector: StringArray, page: Page, callback: (users: User[]) => void){
  console.log('findUsers,page is ' + JSON.stringify(page));
  var sqlBuilder = new SqlBuilder('USER', selector, page);
  sqlBuilder.build();
  var sql = sqlBuilder.sql;
  var insert = sqlBuilder.insert.values; 

  console.log ('sql = ' +sql);
  console.log ('insert' +insert); 
  db.query(sql, insert, function(err, result) {
    if(err) throw err;
    callback(result);
  });
};


export function addPunchCard(username: string, location: string, dept: string, createdate: Date, state: string, callback: (punchCard: PunchCard) => void){
  db.query('INSERT INTO PUNCH_CARD SET ?', {USERNAME:username, LOCATION:location, DEPT:dept, CREATE_DATE:createdate, STATE:state}, function(err, result){
         if (err) throw err;
         console.log(result.insertId);
       });
};


export function findPunchCards(selector: StringArray, page: Page, callback: (punchCards: PunchCard[]) => void){
  console.log('findPunchCards, page is ' + JSON.stringify(page));
  var sqlBuilder = new SqlBuilder('PUNCH_CARD', selector, page);
  sqlBuilder.build();
  var sql = sqlBuilder.sql;
  var insert = sqlBuilder.insert.values; 

  console.log ('sql = ' +sql);
  console.log ('insert ' +insert); 
  
  db.query(sql, insert, function(err, result) {
    if(err) throw err;
    callback(result);
  });
};
