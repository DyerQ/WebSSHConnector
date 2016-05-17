function handleLoginRequest(xhr, status, args) {
    if (!args.loggedIn) {
        PF('dlg_L').jq.effect("shake", {times: 5}, 100);
    } else {
        PF('dlg_L').hide();
        $('#loginLink').fadeOut();
        location.reload();
    }
}
(function() {

    var KEY_ENTER = 13;

    var inputs = document.querySelectorAll('password[id=password]');
    for(var i = 0; i < inputs.length; i++) (function(i){
        function hidePassword(){
            inputs[i].id = 'password';
        }
        function showPassword(){
            inputs[i].id = 'text';
        }
        inputs[i].addEventListener('focus', showPassword, false);
        inputs[i].addEventListener('blur', hidePassword, false);
        inputs[i].addEventListener('keydown', function onBeforeSubmit(e){
            if (e.keyCode === KEY_ENTER) hidePassword();
        }, false);
    })(i);

})();
function handleSignUpRequest(xhr, status, args) {
    if (!args.registerSuccess) {
        PF('dlg_R').jq.effect("shake", {times: 5}, 100);
    }
    else{
        PF('dlg_R').hide();
        $('#loginLink').fadeOut();
    }
}