$(function () {
    var data;
    document.getElementById("login-button").addEventListener("click", login);
    document.getElementById("logout-button").addEventListener("click", logout);
    document.getElementById("signup-button").addEventListener("click", signup);
    document.getElementById("create-button").addEventListener("click", createGame);


    loadPage();

    function loadPage() {
        $.ajax({
            type: 'GET',
            url: '/api/games',
            success: function (data) {

                var currentPlayer = data.player; //undefined if not logged in

                var games = data.games;
                var leaderboard = data.leaderboard;
                console.log(games);
                console.log(leaderboard);
                console.log(currentPlayer);

                if (currentPlayer != undefined) {
                    $("#login-form").addClass("hidden");
                    $("#create-form").removeClass("hidden");
                    $("#logout-form").removeClass("hidden");
                    $("#gamelist_title").html("Welcome " + currentPlayer.username + " !");

                } else if (currentPlayer == undefined) {
                    $("#gamelist_title").html("Are you ready to Salvo???");
                }
                createGameList(games);
                createLeaderboard(leaderboard);
                addButtons(games, currentPlayer);
            }
        });
    }

    function login(evt) {
        console.log(data);
        evt.preventDefault();
        var form = evt.target.form;
        $.post("/api/login", {
                username: form["username"].value,
                password: form["password"].value
            })
            .done(function () {
                console.log("Login successful!");
                loadPage();
            })
            .fail(function () {
                console.log("Login failed!");
                alert("Login failed, please check your username and password again!");
            })
    }

    function logout(evt) {
        evt.preventDefault();
        $.post("/api/logout")
            .done(function () {
                console.log("Logout successful!");
                $("#gamelist_title").html("Are you ready to Salvo???");
                $("#logout-form").addClass("hidden");
                $("#create-form").addClass("hidden");
                $("#login-form").removeClass("hidden");
                $("#login-form input").val("");
                $(".return").remove("");
                $(".join").remove();
            })
            .fail(function () {
                alert("Logout failed, please try again!");
            })
    }

    function signup(evt) {
        console.log(data);
        evt.preventDefault();
        var form = evt.target.form;
        $.post("/api/players", {
                username: form["username"].value,
                password: form["password"].value
            })
            .done(function () {
                console.log("Signup successful!");
                loadPage();
            })
            .fail(function () {
                alert("Signup failed, please try again!");
            })
    }

    function createGame(evt) {
        console.log(data);
        evt.preventDefault();
        var form = evt.target.form;
        $.post("/api/games")
            .done(function (response) {
                console.log("New game created");
                location.href = "game.html?gp=" + response.newGamePlayerId;
            })
            .fail(function () {
                alert("Creating a new game failed, please try again!")
            })
    }

    function joinGame(gameID) {
        $.post("/api/game/" + gameID + "/players")
            .done(function (response) {
                console.log("New gameplayer added");
                location.href = "game.html?gp=" + response.joinedGamePlayerId;
            })
            .fail(function () {
                alert("Joining game failed, please try again!")
            })
    }

    function createGameList(games) {

        if ($("#gamelist").is(":empty")) {
            for (var i = 0; i < games.length; i++) {
                var creationDate = new Date(games[i].created).toLocaleString();
                var gameID = games[i].id;
                var player1 = games[i].gamePlayers[0].player.username;
                if (games[i].gamePlayers.length == 2) {
                    var player2 = games[i].gamePlayers[1].player.username;

                    $("#gamelist").append("<li id=\"game" + gameID + "\">" + creationDate + ": " + player1 + " vs. " + player2 + "</li>");
                } else { // (gamesPlayers.length == 1)
                    $("#gamelist").append("<li id=\"game" + gameID + "\">" + creationDate + ": " + player1 + " vs. " + "waiting for player" + "</li>");
                }

            }
        }
    }


    function addButtons(games, currentPlayer) {

        console.log(currentPlayer);
        console.log(games);

        if (currentPlayer != undefined) {
            for (var i = 0; i < games.length; i++) {
                var gameID = games[i].id;
                var player1 = games[i].gamePlayers[0].player.username;
                if (games[i].gamePlayers.length == 2) { // return to game if I am in the game
                    var userName = currentPlayer.username;
                    var player2 = games[i].gamePlayers[1].player.username;
                    if (player1 == userName) {
                        $("#game" + gameID).append("<input type=\"button\" class=\"return\" onclick=\"location.href=\'game.html?gp=" + games[i].gamePlayers[0].id + "\'\" value=\"Return to Game\" />");
                    } else if (player2 == userName) {
                        $("#game" + games[i].id).append("<input type=\"button\" class=\"return\" onclick=\"location.href=\'game.html?gp=" + games[i].gamePlayers[1].id + "\'\" value=\"Return to Game\" />");
                    }
                } else { // if (games[i].gamePlayers.length == 1)
                    if (player1 == userName) { // If I am the player, I can return
                        $("#game" + gameID).append("<input type=\"button\" class=\"return\" onclick=\"location.href=\'game.html?gp=" + games[i].gamePlayers[0].id + "\'\" value=\"Return to Game\" />");
                    } else { // It´s not me, so I can join!
                        $("#game" + gameID).append("<input type=\"button\" data-game=\"" + gameID + "\" id=\"join-button"+ gameID +"\" value=\"Join Game\" />");
                        $("#join-button"+ gameID).on("click", function (evt) {
                            evt.preventDefault;
                            joinGame(gameID);
                        });
                    }
                }
            }
        }
    }

    function createLeaderboard(leaderboard) {
        if ($("#leaderboard").is(":empty")) {
            $("#leaderboard").append("<tr><th>Name</th><th>Total</th><th>Won</th><th>Lost</th><th>Tied</th></tr>");
            for (var i = 0; i < leaderboard.length; i++) {
                var total = (leaderboard[i].total_score).toFixed(1);
                $("#leaderboard").append("<tr><td>" + leaderboard[i].name + "</td>" + "<td>" + total + "</td>" + "<td>" + leaderboard[i].total_wins + "</td>" + "<td>" + leaderboard[i].total_losses + "</td>" + "<td>" + leaderboard[i].total_ties + "</td></tr>")
            }
        }
    }

});
