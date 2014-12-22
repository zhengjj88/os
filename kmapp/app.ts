import http = require('http')
import url = require('url')
import express = require('express')
import logger = require("morgan")
import cookieParser = require("cookie-parser")
import bodyParser = require("body-parser")
import winston = require('winston')
 
import db = require("./db")
var app = express();

class Response {
  code: string;
  msg: string;
  data: any;
  constructor(code: string, msg: string, data: any){
    this.code = code;
    this.msg = msg;
    this.data = data;
  }
}

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

//add user
app.post('/api/v1/user',function(req, res) {
  console.log("add user " + JSON.stringify(req.body));
  var response = new Response('','',{});
  
  if(req.body.username == undefined){
    response.code = 'F0001';
    response.msg = 'username is empty';
  }
  else if(req.body.password== undefined){
    response.code = 'F0002';
    response.msg = 'password is empty';
  }
  else if(req.body.realname == undefined){
    response.code = 'F0003';
    response.msg = 'realname is empty'
  }
  else if(req.body.imsi == undefined){
    response.code = 'F0004';
    response.msg = 'imis is empty';
  }
  else if(req.body.dept == undefined){
    response.code = 'F0005';
    response.msg = 'dept is empty';
  }
  else if(req.body.subCompany == undefined){
    response.code = 'F0006';
    response.msg = 'subcompany is emtpy';
  }
  
  //return if invalid
  if(response.code !=''){
    res.status(400).send(JSON.stringify(response));
    return;
  }
  
  db.addUser(req.body.username, req.body.password, req.body.realname, new Date(), req.body.imsi, req.body.dept, req.body.subCompany, 'Y', function(user){
  });

  response = new Response('00', 'Add user successfully', {});
  res.status(200).send(JSON.stringify(response));
});
//query user
app.get('/api/v1/user', function(req, res){
  console.log("query user " + JSON.stringify(req.query));
  var response = new Response('','',{});

  if((req.query.username != undefined) || (req.query.imsi != undefined)){
    console.log("query user by username or passowrd");
    var selector: db.StringArray = {};
    if(req.query.username != undefined)
      selector['USERNAME'] = req.query.username;
    if(req.query.imsi != undefined)
      selector['IMSI'] = req.query.imsi;
    db.findUsers(selector, null, function(users){
      response.code = '00';
      response.data = users;
      res.status(200).send(JSON.stringify(response));
    });
  }
  else{
    console.log("query user all"); 
    var pageSize = req.query.pageSize;
    var pageNumber = req.query.pageNumber;
debugger;
    if(pageSize == undefined){
      pageSize = 20;
    }
    
    if(pageNumber == undefined) {
      pageNumber = 0;
    }
    winston.info('befre construct, pageNumber = ' + pageNumber + ', pageSize= ' +pageSize);
    var page = new db.Page(pageNumber, pageSize);
    winston.info('after construct, rowCount = ' +page.rowCount +', offset = ' + page.offset);
    db.findUsers(null, page, function(users) {
      response.code = '00';
      response.data = users;
      res.status(200).send(JSON.stringify(response));
    });
  }
});


//add punch card
app.post('/api/v1/user/:username/punchcard',function(req, res) {
  console.log("add punch card " + JSON.stringify(req.body));
  var response = new Response('','',{});
  
  if(req.params.username == undefined){
    response.code = 'F0001';
    response.msg = 'username is empty';
  }
  else if(req.body.location== undefined){
    response.code = 'F0009';
    response.msg = 'location is empty';
  }
  else if(req.body.dept == undefined){
    response.code = 'F005';
    response.msg = 'dept is empty';
  }
  
  //return if invalid
  if(response.code !=''){
    res.status(400).send(JSON.stringify(response));
    return;
  }
  
  db.addPunchCard(req.params.username, req.body.location, req.body.dept, new Date(), '', function(punchCard){
  });

  response = new Response('00', 'Add punch card successfully', {});
  res.status(200).send(JSON.stringify(response));
});

app.listen(3000, function(){
    console.log("Demo Express server listening on port %d in %s mode", 3000, app.settings.env);
});


function buildPage(req): db.Page {
    var pageSize = req.query.pageSize;
    var pageNumber = req.query.pageNumber;
    if(pageSize == undefined){
      pageSize = 20;
    }
    if(pageNumber == undefined) {
      pageNumber == 0;
    }
    var page = new db.Page(pageNumber, pageSize);
    return page;
}

//query punch card
/*
app.get('/api/v1/user/punchcard', function(req, res){
  console.log("query punch card all " + JSON.stringify(req.query));
  var response = new Response('','',{});

  if(req.query.dept != undefined){//manager
    console.log('query punch card by dept');
    var page: db.Page = buildPage(req);

    var selector: db.StringArray = {};
    if(req.query.dept!= undefined)
      selector['DEPT'] = req.query.dept;
    
    db.findPunchCards(selector, page, function(punchCards){
      response.code = '00';
      response.data = punchCards;
      res.status(200).send(JSON.stringify(response));
    });
  }else if(req.query.username != undefined) {//employee
    console.log('query punch card by user');
  }  
});
*/

//query punchard by self
app.get('/api/v1/user/:username/punchcard', function(req, res){
  console.log("query punch card by self  " + JSON.stringify(req.query));
  var response = new Response('','',{});

  req.query.username = req.params.username;
  var page: db.Page = buildPage(req);

  var selector: db.StringArray = {};
  selector['USERNAME'] = req.query.username;
  
  if(req.query.createdate != undefined) {
    selector['CREATE_DATE'] = req.query.createdate;
  }  
  console.log ('selector is ' + JSON.stringify(selector));

  db.findPunchCards(selector, page, function(punchCards){
    response.code = '00';
    response.data = punchCards;
    res.status(200).send(JSON.stringify(response));
  });
});

export var App = app;
