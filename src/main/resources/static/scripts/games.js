$(function () {
    var data;
    document.getElementById("login-button").addEventListener("click", login);
    document.getElementById("logout-button").addEventListener("click", logout);
    document.getElementById("signup-button").addEventListener("click", signup);
    
    loadPage();

    function loadPage() {
        $.ajax({
            type: 'GET',
            url: '/api/games',
            success: function (data) {
                var player = data.player;
                var games = data.games;
                var leaderboard = data.leaderboard;
                console.log(games);
                console.log(leaderboard);

                if (data.player != undefined) {
                    $("#login-form").addClass("hidden");
                    $("#logout-form").removeClass("hidden");
                    $("#gamelist_title").html("Welcome " + data.player.username + " !");
                } else if (data.player == undefined) {
                    $("#gamelist_title").html("Are you ready to Salvo???");
                }
                createGameList(games);
                createLeaderboard(leaderboard);
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
        $.post("/api/logout", )
            .done(function () {
                console.log("Logout successful!");
                $("#gamelist_title").html("Are you ready to Salvo???");
                $("#logout-form").addClass("hidden");
                $("#login-form").removeClass("hidden");
                $("#login-form input").val("");
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

    function createGameList(games) {
        if ($("#gamelist").is(":empty")) {
            for (var i = 0; i < games.length; i++) {
                var creationDate = new Date(games[i].created).toLocaleString();
                var player1 = games[i].gamePlayers[0].player.username;
                var player2 = games[i].gamePlayers[1].player.username;

                $("#gamelist").append("<li>" + creationDate + ": " + player1 + " vs. " + player2 + "</li>");
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
