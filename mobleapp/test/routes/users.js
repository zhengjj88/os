var express = require('express');
var router = express.Router();
var inspect = require('util').inspect;

var c = require('./db');
/* GET users listing. */
router.get('/', function(req, res) {
  var qc = {};
  qc.username = req.query['username'];
  qc.imsi = req.query['imsi'];
  qc.pageNumber = req.query['pageNumber'];
  qc.pageSize = req.query['pageSize'];
  
  var userinfo ;
  if(qc.username == undefined && qc.imsi == undefined){
    console.log('find all user');
    if(qc.pageNumber == undefined || qc.pageSize == undefined){
      res.status(400).json({'code':'10'});
      return;
    }
  } 
  var querySql="SELECT t.USERNAME,t.REAL_NAME,t.CREATE_DATE,t.IMSI,t.DEPT,t.SUB_COMPANY,t.ENABLE FROM USER t ";
  var whereCause = "WHERE";
console.log("2....................."+qc.username);
//check username
  if(!(qc.username == undefined)){
    if (whereCause.indexOf("=")==-1){
      whereCause = whereCause.concat(" t.username = :username");
    }else{
      whereCause = whereCause.concat(" and t.username = :username"); 
    }
  }
  console.log('..........................');   
//check imsi 
  if(!(qc.imsi == undefined)){
    if (whereCause.indexOf("=")==-1){
      whereCause = whereCause.concat(" t.imsi= :imsi");
    }else{
      whereCause = whereCause.concat(" and t.imsi= :imsi"); 
    }
  }

 //build the query sql 
  querySql = querySql.concat(whereCause);
  console.log('querySql = ' +querySql);
  var cb = function(error,records){
    if(error)
      throw error;
    else{ 
        res.status(200).json(records);
    }
  };
  cb.pageNumber = 1 ;
  cb.pageSize = 10;
  cb.querySql = querySql;
  cb.qc = qc;
  c.QueryRecord(cb);
/*  userinfo = c.QueryRecord(function(error,records){
    if(error)
      throw error;
    else{ 
//      console.log(records);
        res.status(200).json(records);
    }
  });
*/

// console.log(userinfo);
//  res.send('respond with a resource,username= '+username +",imsi="+imsi);
// res.status(200).json(userinfo);
});

//add by jack 20141126 for punch card

/* Add punchcard */
router.post('/:username/punchcard',function(req,res){
  var username = req.params.username;
  var location = req.body.location;
  var dept = req.body.dept;
  var state = req.body.state;
  console.log(JSON.stringify(req.body));

c.query('INSERT INTO PUNCH_CARD (USERNAME,LOCATION,DEPT,CREATE_DATE,STATE) VALUES(:name,:location,:dept,CURRENT_TIMESTAMP,:state)',
        { name: username,
	location: location,
	dept: dept,
        state: state
	})
 .on('result', function(res) {
   res.on('row', function(row) {
     console.log('Result row: ' + inspect(row));
   })
   .on('error', function(err) {
     console.log('Result error: ' + inspect(err));
   })
   .on('end', function(info) {
     console.log('Result finished successfully');
   });
 })
 .on('end', function() {
   console.log('Done with all results');
 });
  res.status(201).json({'hello':'hello world'});
});

/*Add user*/
router.post('/',function(req,res){
  var username = req.body.username;
  var password = req.body.password;
  var realname = req.body.realname;
  var imsi = req.body.imsi;
  var dept = req.body.dept;
  var subCompany = req.body.subCompany;
  console.log('username='+username+",password=******,realname="+realname+",imsi="+imsi+",dept="+dept+",subCompany="+subCompany);

c.query("INSERT INTO USER (USERNAME,PASSWORD,REAL_NAME,CREATE_DATE,IMSI,DEPT,SUB_COMPANY,ENABLE) VALUES(:name,:password,:realname,CURRENT_TIMESTAMP,:imsi,:dept,:subcompany,'Y')",
        { name: username,
	password:password,
	realname:realname,
	imsi:imsi,
	dept:dept,
	subcompany:subCompany })
 .on('result', function(res) {
   res.on('row', function(row) {
     console.log('Result row: ' + inspect(row));
   })
   .on('error', function(err) {
     console.log('Result error: ' + inspect(err));
   })
   .on('end', function(info) {
     console.log('Result finished successfully');
   });
 })
 .on('end', function() {
   console.log('Done with all results');
 });
  res.status(201);
  res.json({username:username,password:password});
});

module.exports = router;
