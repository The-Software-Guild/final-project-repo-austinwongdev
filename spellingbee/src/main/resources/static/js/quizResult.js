$(document).ready(function () {
    $('.guessTableRow').each(function(){
        if ($(this).find("td.isCorrect").text() === 'false'){
            $(this).find("td.guessGuess").css({'color':'firebrick', 'font-weight': 'bold'});
        }
    });
});