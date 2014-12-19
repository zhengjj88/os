var Sequelize = require('sequelize')
  , sequelize = new Sequelize('DEMODB','root','123456',{
      dialect: "mariadb",
      port: 3306
    });
  sequelize.authenticate()
  .complete(function(err){
    if(!!err){
      console.log('Unable to connect to the database:',err);
    }else{
      console.log('Connection has been established successfully.');
    }
  });
