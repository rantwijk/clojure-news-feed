'use strict';

exports.addFriend = function(args, callback) {
  /**
   * parameters expected in the args:
  * body (Friend)
  **/
  const pool = require('../repositories/mysql').pool;
  const mysql = require('mysql');
  pool.getConnection(function(err, conn) {
      if (err) {
	  callback(err, null);
	  return;
      }
      conn.query(mysql.format("call UpsertFriends(?, ?)", [args.body.value.from, args.body.value.to]), function (err, rows) {
	  if (err) {
	      conn.release();
	      callback(err, null);
	      return;
	  }
	  var result = rows[0].map(function(row) {
	      return {
		  'id':row['id'],
		  'from':args.body.value.from, 
	          'to': args.body.value.to };
	  });
	  conn.release();
	  callback(null, result);
      });
  });
  
}

exports.getFriend = function(args, callback) {
  /**
   * parameters expected in the args:
  * id (Long)
  **/
  const mysql = require('mysql');
  const pool = require('../repositories/mysql').pool;
  const redis = require('../repositories/redis');
  const key = 'Friend::'.concat(args.id.value);
  redis.getCache(function(cache) {
      cache.get(key, function (err, reply) {
	  if (err) {
	      cache.quit();
	      callback(err, null);
	      return;
	  }
	  if (reply == null) {
	      pool.getConnection(function(err, conn) {
		  if (err) {
		      cache.quit();
		      callback(err, null);
		      return;
		  }
		  conn.query(mysql.format("call FetchFriends(?)", [args.id.value]), function (err, rows) {
		      if (err) {
			  conn.release();
			  cache.quit();
			  callback(err, null);
			  return;
		      }
		      var result = rows[0].map(function(row) {
			  return {
			      'id':row['FriendsID'], 
			      'from':args.id.value,
			      'to':row['ParticipantID']};
		      });
		      const retVal = JSON.stringify(result || {});
		      cache.set(key, retVal);
		      conn.release();
		      cache.quit();
		      callback(null, result);
		  });
	      });
	  } else {
	      cache.quit();
	      callback(null, JSON.parse(reply));
	  }
      });
  });
  
}

