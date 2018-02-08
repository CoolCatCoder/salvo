$(function () {
    var $games = $('#games'); //Here we cache the DOM so we only have to look at it once
    $.ajax({
        type: 'GET',
        url: '/api/games',
        success: function (games) {

            $.each(games, function (i, game) {

                var creationDate = new Date(game.created).toLocaleString();

                console.log(creationDate);
                // I have to change the games, beacuse there will be only 2 players and also every game needs a player to be created.      

                if (game.gamePlayers.length > 0) {
                    //                    $games.append('<li>' + creationDate + ':');
                    //                    var userNames = [];
                    //                    for (var w = 0; w < game.gamePlayers.length; w++) {
                    //                        userNames.push(game.gamePlayers[w].player.userName);
                    //                    }
                    $games.append('<li>' + creationDate + ': ' + game.gamePlayers[0].player.userName + ', ' + game.gamePlayers[1].player.userName + '</li>');

                } else {
                    $games.append('<li>' + creationDate + '</li>');
                }
            });
        }
    });
});
