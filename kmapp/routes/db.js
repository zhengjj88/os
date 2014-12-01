var inspect = require('util').inspect;
var Client = require('mariasql');
var c = new Client();
c.connect({
  host:'127.0.0.1',
  user:'root',
  password:'123456',
  db:'KMAPPDB'
});
c.on('connect',function(){
  console.log('Client connected');
})
.on('error',function(err){
  console.log('Client error :'+err);
})
.on('close',function(hadError){
  console.log('Client closed');
});

var QueryRecord = function(cb){
  var records = new Array(2);
  var error;
  var i = 0;
  var pageSize = cb.pageSize;
  var pageNumber = cb.pageNumber;
  console.log("pageSize = " + pageSize + ", pageNumber = " + pageNumber);
  c.query(cb.querySql,cb.qc)
  .on('result',function(res){
      res.on('row',function(row){
       // records = inspect(row); 
        console.log(row);
        records[i++] = row; 
      })
      .on('error',function(err){
        console.log('Result error: ' +inspect(err));
        error = err;
      })
      .on('end',function(info){
        console.log('Result finished successfully');
      });
     })
  .on('end',function(){
      console.log('Done with all results');
      cb(error,records); 
   });
};

c.QueryRecord = QueryRecord;
module.exports = c;


