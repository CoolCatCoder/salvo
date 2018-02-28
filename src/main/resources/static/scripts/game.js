$(function () {

    var numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    //console.log(numbers[7]);

    var letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    //console.log(letters[2]);

    document.getElementById("logout-button").addEventListener("click", logout);

    $.ajax({
        type: 'GET',
        url: '/api/game_view/' + pullGamePlayerID(),
        success: function (game_view) {

            console.log(gameplayerID);
            showGameInfo(game_view);
            renderHeaders(numbers);
            renderShipRows(letters);
            renderSalvoRows(letters);
            showMyShips(game_view);
            showMySalvoes(game_view);
        }
    });

    function getHeadersHtml(numbers) {
        return "<tr><th></th>" + numbers.map(function (number) {
            return "<th>" + number + "</th>";
        }).join("") + "</tr>";
    }

    function renderHeaders(numbers) {
        var html = getHeadersHtml(numbers);
        document.getElementById("table-headers").innerHTML = html;
        document.getElementById("salvo-headers").innerHTML = html;
    }

    function getShipRowsHtml(letters) {
        return "<tr>" + letters.map(function (letter) {

            return "<th>" + letter + "</th>" + makeGrid(letters) + "</tr>";

            function makeGrid(letters) {

                return letters.map(function (i, number) {

                    return "<td id=\"" + letter + (number + 1) + "\"></td>";

                }).join("");
            }
        }).join("");
    }

    function renderShipRows(letters) {
        var html = getShipRowsHtml(letters);
        document.getElementById("table-rows").innerHTML = html;
    }

    function getSalvoRowsHtml(letters) {
        return "<tr>" + letters.map(function (letter) {

            return "<th>" + letter + "</th>" + makeGrid(letters) + "</tr>";

            function makeGrid(letters) {

                return letters.map(function (i, number) {

                    return "<td id=\"" + "s" + letter + (number + 1) + "\"></td>";

                }).join("");
            }
        }).join("");
    }

    function renderSalvoRows(letters) {
        var html = getSalvoRowsHtml(letters);
        document.getElementById("salvo-rows").innerHTML = html;
    }

    function pullGamePlayerID() {
        var query = window.location.search.substring(1);
        //console.log(query);
        var vars = query.split("&");
        //console.log(vars);
        for (var i = 0; i < vars.length; i++) {
            let pair = vars[i].split("=");
            //console.log(pair[i]);
            //console.log(pair);
            if (pair[0] == "gp") {
                gameplayerID = pair[1];
                return pair[1];
            }
        }
        return (false);
    }

    function showMyShips(game_view) {

        var ships = game_view.ships;
        var allShipsLocations = [];
        console.log(ships);

        for (var i = 0; i < ships.length; i++) {
            for (var w = 0; w < ships[i].locations.length; w++) {
                allShipsLocations.push(ships[i].locations[w]);
            }
        }

        for (var i = 0; i < allShipsLocations.length; i++) {
            $("#" + allShipsLocations[i]).css('background-color', 'grey');
        }

        showOppSalvoes(game_view);
    }

    function showMySalvoes(game_view) {
        var gamePlayers = game_view.gamePlayers;
        var viewerID = pullGamePlayerID();
        var allSalvoes = game_view.salvoes;

        var mySalvoes = {};

        if (gamePlayers[0].id == viewerID) {
            console.log(gamePlayers[0].player.id);
            mySalvoes = allSalvoes[gamePlayers[0].player.id];
            var mySalvoesTurn1 = mySalvoes[1];
            var mySalvoesTurn2 = mySalvoes[2];
            console.log(mySalvoesTurn1);
            console.log(mySalvoesTurn2);

            for (var i = 0; i < mySalvoesTurn1.length; i++) {
                $("#" + "s" + mySalvoesTurn1[i]).css('background-color', 'orange').html(1);
            }

            for (var i = 0; i < mySalvoesTurn2.length; i++) {
                $("#" + "s" + mySalvoesTurn2[i]).css('background-color', 'orange').html(2);
            }

        } else {
            console.log(gamePlayers[1].player.id);
            mySalvoes = allSalvoes[gamePlayers[1].player.id];
            var mySalvoesTurn1 = mySalvoes[1];
            var mySalvoesTurn2 = mySalvoes[2];
            console.log(mySalvoesTurn1);
            console.log(mySalvoesTurn2);

            for (var i = 0; i < mySalvoesTurn1.length; i++) {
                $("#" + "s" + mySalvoesTurn1[i]).css('background-color', 'orange').html(1);
            }

            for (var i = 0; i < mySalvoesTurn2.length; i++) {
                $("#" + "s" + mySalvoesTurn2[i]).css('background-color', 'orange').html(2);
            }
        }

    }

    function showOppSalvoes(game_view) {
        var gamePlayers = game_view.gamePlayers;
        var viewerID = pullGamePlayerID();
        var allSalvoes = game_view.salvoes;

        var oppSalvoes = {};

        if (gamePlayers[0].id == viewerID) {
            console.log(gamePlayers[0].player.id);
            oppSalvoes = allSalvoes[gamePlayers[1].player.id];
            var oppSalvoesTurn1 = oppSalvoes[1];
            var oppSalvoesTurn2 = oppSalvoes[2];
            console.log(oppSalvoesTurn1);
            console.log(oppSalvoesTurn2);

            for (var i = 0; i < oppSalvoesTurn1.length; i++) {

                console.log($('#' + oppSalvoesTurn1[i]).css('background-color'));

                if ($('#' + oppSalvoesTurn1[i]).css('background-color') == 'rgb(128, 128, 128)') {
                    $('#' + oppSalvoesTurn1[i]).css('background-color', 'rgb(255, 165, 0)').html(1);
                }
            }

            for (var i = 0; i < oppSalvoesTurn2.length; i++) {
                if ($('#' + oppSalvoesTurn2[i]).css('background-color') == 'rgb(128, 128, 128)') {
                    $('#' + oppSalvoesTurn2[i]).css('background-color', 'rgb(255, 165, 0)').html(2);
                }
            }

        } else {
            console.log(gamePlayers[0].player.id);
            oppSalvoes = allSalvoes[gamePlayers[0].player.id];
            var oppSalvoesTurn1 = oppSalvoes[1];
            var oppSalvoesTurn2 = oppSalvoes[2];
            console.log(oppSalvoesTurn1);
            console.log(oppSalvoesTurn2);

            for (var i = 0; i < oppSalvoesTurn1.length; i++) {

                console.log($('#' + oppSalvoesTurn1[i]).css('background-color'));

                if ($('#' + oppSalvoesTurn1[i]).css('background-color') == 'rgb(128, 128, 128)') {
                    $('#' + oppSalvoesTurn1[i]).css('background-color', 'rgb(255, 165, 0)').html(1);
                }
            }

            for (var i = 0; i < oppSalvoesTurn2.length; i++) {
                if ($('#' + oppSalvoesTurn2[i]).css('background-color') == 'rgb(128, 128, 128)') {
                    $('#' + oppSalvoesTurn2[i]).css('background-color', 'rgb(255, 165, 0)').html(2);
                }
            }
        }
    }

    function logout(evt) {
        evt.preventDefault();
        $.post("/api/logout", )
            .done(function () {
                setTimeout(function () {
                    window.location.href = "games.html";
                }, 3000);
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


    function showGameInfo(game_view) {

        var gamePlayers = game_view.gamePlayers;

        var viewerID = pullGamePlayerID();

        console.log(viewerID);

        if (gamePlayers[0].player.id == viewerID) {
            $("#tableContainer").before("<h2>" + gamePlayers[0].player.username + " vs. " + gamePlayers[1].player.username + "<h2>");
        } else {
            $("#tableContainer").before("<h2>" + gamePlayers[1].player.username + " vs. " + gamePlayers[0].player.username + "</h2>");
        }


    }
});
