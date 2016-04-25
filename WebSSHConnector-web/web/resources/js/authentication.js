function handleLoginRequest(xhr, status, args) {
    if (!args.loggedIn) {
        PF('dlg_L').jq.effect("shake", {times: 5}, 100);
    } else {
        PF('dlg_L').hide();
        $('#loginLink').fadeOut();
    }
}