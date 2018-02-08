$(function () {

    var numbers = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    console.log(numbers[7]);

    var letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    console.log(letters[2]);

    $.ajax({
        type: 'GET',
        url: '/api/game_view/' + pullGamePlayerID(),
        success: function (game_view) {
            console.log(gameplayerID);
            renderHeaders(numbers);
            renderRows(letters);
            showShips(game_view);
            showGameInfo(game_view);
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
    }

    function getRowsHtml(letters) {
        return "<tr>" + letters.map(function (letter) {

            return "<th>" + letter + "</th>" + makeGrid(letters) + "</tr>";

            function makeGrid(letters) {

                return letters.map(function (i, number) {

                    return "<td id=\"" + letter + (number + 1) + "\"></td>";

                }).join("");
            }
        }).join("");
    }

    function renderRows(letters) {
        var html = getRowsHtml(letters);
        document.getElementById("table-rows").innerHTML = html;
    }

    function pullGamePlayerID() {
        var query = window.location.search.substring(1);
        console.log(query);
        var vars = query.split("&");
        console.log(vars);
        for (var i = 0; i < vars.length; i++) {
            let pair = vars[i].split("=");
            console.log(pair[i]);
            console.log(pair);
            if (pair[0] == "gp") {
                gameplayerID = pair[1];
                return pair[1];
            }
        }
        return (false);
    }

    function showShips(game_view) {

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
    }

    function showGameInfo(game_view) {
        var gamePlayers = game_view.gamePlayers;

        var viewerID = pullGamePlayerID();

        console.log(viewerID);
        
    if (gamePlayers[0].id == viewerID){
       $("#gameTable").before("<h2>" + gamePlayers[0].player.userName + " vs. " + gamePlayers[1].player.userName + "<h2>");
    }else {
        $("#gameTable").before("<h2>" + gamePlayers[1].player.userName + " vs. " + gamePlayers[0].player.userName + "</h2>");
    }

        
//        if (viewerID === gamePlayers[0].player.id) {
//            $("#gameTable").before("<h2>" + gamePlayers[0].player.userName + " vs. " + gamePlayers[1].player.userName + "<h2>");
//        } else {
//            $("#gameTable").before("<h2>" + gamePlayers[1].player.userName + " vs. " + gamePlayers[0].player.userName + "</h2>");
//        }



    }
});
