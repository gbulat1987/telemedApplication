document.addEventListener('DOMContentLoaded', function () {
    console.log("Skripta je učitana i DOM je spreman.");

    const togglePasswordButton = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    const toggleIcon = document.getElementById('toggleIcon');

    if (!togglePasswordButton) console.error("Element togglePasswordButton nije pronađen.");
    if (!passwordInput) console.error("Element passwordInput nije pronađen.");
    if (!toggleIcon) console.error("Element toggleIcon nije pronađen.");

    togglePasswordButton.addEventListener('click', function () {
        console.log("Klik na togglePasswordButton.");
        if (passwordInput.type === 'password') {
            console.log("Prikazivanje lozinke.");
            passwordInput.type = 'text';
            toggleIcon.classList.remove('bi-eye');
            toggleIcon.classList.add('bi-eye-slash');
        } else {
            console.log("Sakrivanje lozinke.");
            passwordInput.type = 'password';
            toggleIcon.classList.remove('bi-eye-slash');
            toggleIcon.classList.add('bi-eye');
        }
    });
});
