$(document).ready(function () {
    toggleDefinition();
    toggleWordExample();
    playDefinitionAudio();
    playWordExampleAudio();
    playPronunciationAudio();
});

function playPronunciationAudio(){
    $('#playPronunciationButton').on('click', function(){
        var pronunciationAudio = document.getElementById("pronunciationAudio"); 
        pronunciationAudio.play();
    });
}

function toggleDefinition() {
    $('#toggleDefinitionButton').on('click', function(){
        $('#wordDefinitionHidingWord').toggle('slow');
        if ($('#toggleDefinitionButton').text() === 'Show'){
            $('#toggleDefinitionButton').text('Hide');
        }
        else {
            $('#toggleDefinitionButton').text('Show');
        }
    });
}

function toggleWordExample() {
    $('#toggleWordExampleButton').on('click', function(){
        $('#wordExampleHidingWord').toggle('slow');
        if ($('#toggleWordExampleButton').text() === 'Show'){
            $('#toggleWordExampleButton').text('Hide');
        }
        else {
            $('#toggleWordExampleButton').text('Show');
        }
    });
}

function playDefinitionAudio() {
    $('#playDefinitionButton').on('click', function(){
        window.speechSynthesis.speak(new SpeechSynthesisUtterance($('#wordDefinition').text()));
    });
}

function playWordExampleAudio() {
    $('#playWordExampleButton').on('click', function(){
        window.speechSynthesis.speak(new SpeechSynthesisUtterance($('#wordExample').text()));
    });
}