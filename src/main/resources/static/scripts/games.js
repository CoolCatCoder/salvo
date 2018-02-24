$(function () {
    var data; //Here we cache the DOM so we only have to look at it once

    $.ajax({
        type: 'GET',
        url: '/api/games',
        success: function (data) {
            var games = data.games;
            var leaderboard = data.leaderboard;
            console.log(games);
            console.log(games[0].gamePlayers[0].player.username);
            console.log(leaderboard);
                                createGameList(games);
                              createLeaderboard(leaderboard);
            
        }
    });

    function createGameList(games) {
        
        for (var i = 0; i < games.length; i++) {
            var creationDate = new Date(games[i].created).toLocaleString();
            var player1 = games[i].gamePlayers[0].player.username;
            var player2 = games[i].gamePlayers[1].player.username;
            
         $("#gamelist").append("<li>" + creationDate + ": " + player1 + " vs. " + player2 + "</li>");
        }
       
    }

    function createLeaderboard(leaderboard){
        
        $("#leaderboard").append("<tr><th>Name</th><th>Total</th><th>Won</th><th>Lost</th><th>Tied</th></tr>");
        for (var i=0; i<leaderboard.length; i++) {
            var total = (leaderboard[i].total_score).toFixed(1);
            $("#leaderboard").append("<tr><td>" + leaderboard[i].name + "</td>" + "<td>" + total + "</td>" + "<td>" + leaderboard[i].total_wins + "</td>" + "<td>" + leaderboard[i].total_losses + "</td>" + "<td>" + leaderboard[i].total_ties + "</td></tr>")
        }
        
    }



});
