var mysql = require("mysql");
var db = mysql.createPool({
    connectionLimit: 10,
    host: 'localhost',
    user: 'root',
    password: '123456',
    database: 'KMAPPDB'
});
db.query('SELECT 1 + 1 AS solution', function (err, rows, fields) {
    if (err)
        throw err;
    console.log('The solution is: ', rows[0].solution);
});
var SqlBuilder = (function () {
    function SqlBuilder(tableName, selector, page) {
        this.insert = {
            values: []
        };
        this.tableName = tableName;
        this.selector = selector;
        this.page = page;
    }
    SqlBuilder.prototype.build = function () {
        var i = 0;
        var whereClause = " WHERE ";
        this.sql = "SELECT * FROM " + this.tableName;
        if (this.selector != null) {
            for (var name in this.selector) {
                if (i > 0) {
                    if (name == 'CREATE_DATE') {
                        console.log('............................' + this.selector[name][0] + ",.........." + this.selector[name][1]);
                        whereClause = whereClause.concat(' and ').concat(name + ' between ? and ? ');
                        this.insert.values.push(this.selector[name][0]);
                        this.insert.values.push(this.selector[name][1]);
                    }
                    else {
                        whereClause = whereClause.concat(' and ').concat(name + ' = ? ');
                        this.insert.values.push(this.selector[name]);
                    }
                }
                else {
                    if (name == 'CREATE_DATE') {
                        whereClause = whereClause.concat(name + ' between ? and ? ');
                        this.insert.values.push(this.selector[name][0]);
                        this.insert.values.push(this.selector[name][1]);
                    }
                    else {
                        whereClause = whereClause.concat(name + ' = ? ');
                        this.insert.values.push(this.selector[name]);
                    }
                }
                i++;
            }
            this.sql = this.sql.concat(whereClause);
        }
        if (this.page != null) {
            this.sql = this.sql.concat(" limit ?, ? ");
            this.insert.values.push(Number(this.page.offset));
            this.insert.values.push(Number(this.page.rowCount));
        }
        console.log('query sql is ' + this.sql);
        console.log('inserts is ' + this.insert.values);
    };
    return SqlBuilder;
})();
var Page = (function () {
    function Page(pageNumber, pageSize) {
        this.rowCount = pageSize;
        this.offset = this.rowCount * pageNumber;
    }
    return Page;
})();
exports.Page = Page;
function addUser(username, password, realname, createdate, imsi, dept, subcompany, enable, callback) {
    db.query('INSERT INTO USER SET ?', { USERNAME: username, PASSWORD: password, REAL_NAME: realname, CREATE_DATE: createdate, IMSI: imsi, DEPT: dept, SUB_COMPANY: subcompany, ENABLE: 'Y' }, function (err, result) {
        if (err)
            throw err;
        console.log(result.insertId);
    });
}
exports.addUser = addUser;
;
function findUsers(selector, page, callback) {
    console.log('findUsers,page is ' + JSON.stringify(page));
    var sqlBuilder = new SqlBuilder('USER', selector, page);
    sqlBuilder.build();
    var sql = sqlBuilder.sql;
    var insert = sqlBuilder.insert.values;
    console.log('sql = ' + sql);
    console.log('insert' + insert);
    db.query(sql, insert, function (err, result) {
        if (err)
            throw err;
        callback(result);
    });
}
exports.findUsers = findUsers;
;
function addPunchCard(username, location, dept, createdate, state, callback) {
    db.query('INSERT INTO PUNCH_CARD SET ?', { USERNAME: username, LOCATION: location, DEPT: dept, CREATE_DATE: createdate, STATE: state }, function (err, result) {
        if (err)
            throw err;
        console.log(result.insertId);
    });
}
exports.addPunchCard = addPunchCard;
;
function findPunchCards(selector, page, callback) {
    console.log('findPunchCards, page is ' + JSON.stringify(page));
    var sqlBuilder = new SqlBuilder('PUNCH_CARD', selector, page);
    sqlBuilder.build();
    var sql = sqlBuilder.sql;
    var insert = sqlBuilder.insert.values;
    console.log('sql = ' + sql);
    console.log('insert ' + insert);
    //  sql ='SELECT * FROM PUNCH_CARD WHERE USERNAME = ?  and CREATE_DATE between ? and ?  limit ?, ?';
    //  insert = ['jack','2014-12-01','2014-12-11',0,2];
    db.query(sql, insert, function (err, result) {
        if (err)
            throw err;
        callback(result);
    });
}
exports.findPunchCards = findPunchCards;
;
