// curpage==0: loginpage
// curpage==1: signuppage
// curpage==2: signupsuccesspage
var curpage = 0;

// if sign up success, change to 1
var signuped = 0;
var email = "";
var password = "";

// if valid input, change to 1
var validFirstname = 0;
var validLastname = 0;
var validGender = 0;
var validEmail = 0;
var validPassword = 0;
var validConfirmpassword = 0;

var validEmail_login = 0;
var validPassword_login = 0;

// change page id str1 to str2
function changepg2(str1,str2) {
    document.getElementById(str1).style.display = "none";
    document.getElementById(str2).style.display = "block";
}

// change page id str1 to str2 with fadein, fadeout effect
function changepg(str1,str2) {
    fadeOut(str1);
    setTimeout(function() {
        fadeIn(str2);
    }, 500);
}

// fadein effect
function fadeIn(id) {
    var element = document.getElementById(id);
    var op = 0.1;
    element.style.display = 'block';
    var timer = setInterval(function () {
        if (op >= 1){
            clearInterval(timer);
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op += op * 0.1;
    }, 10);
}

// fadeout effect
function fadeOut(id) {
    var element = document.getElementById(id);
    var op = 1;
    var timer = setInterval(function () {
        if (op <= 0.1){
            clearInterval(timer);
            element.style.display = 'none';
        }
        element.style.opacity = op;
        element.style.filter = 'alpha(opacity=' + op * 100 + ")";
        op -= op * 0.1;
    }, 10);
}

function loginclick() {
    if (curpage == 1 || curpage == 2) {
        document.getElementById("signupheaderp").style.backgroundColor = 'rgb(217,217,217)';
        document.getElementById("signupheader").style.backgroundColor = 'rgb(217,217,217)';
        document.getElementById("signupheader").style.color = 'rgb(120,120,120)';

        document.getElementById("loginheaderp").style.backgroundColor = '#2F558E';
        document.getElementById("loginheader").style.backgroundColor = 'rgb(242,242,242)';
        document.getElementById("loginheader").style.color = 'black';
        if (curpage==1) changepg("signup", "login");
        else changepg("signupsuccess", "login");
        curpage = 0;
    }
}

function signupclick() {
    if (curpage == 0) {
        curpage = 1;
        document.getElementById("loginheaderp").style.backgroundColor = 'rgb(217,217,217)';
        document.getElementById("loginheader").style.backgroundColor = 'rgb(217,217,217)';
        document.getElementById("loginheader").style.color = 'rgb(120,120,120)';

        document.getElementById("signupheaderp").style.backgroundColor = '#2F558E';
        document.getElementById("signupheader").style.backgroundColor = 'rgb(242,242,242)';
        document.getElementById("signupheader").style.color = 'black';
        
        document.getElementById("infologin").style.color = "white";
        document.getElementById("infologin").textContent = "Enter user name and password:";
        changepg("login", "signup");
    }
}

function signupbutton() {
    var success = 0;
    handleFirstname(document.getElementById("signupfirstname").value);
    handleLastname(document.getElementById("signuplastname").value);
    handleEmail(document.getElementById("signupemail").value);
    handlePassword(document.getElementById("signuppassword").value);
    handleConfirmPassword(document.getElementById("signupconfirmpassword").value);
    handleGender();
    
    if (validFirstname&&validLastname&&validGender&&validEmail&&validPassword&&validConfirmpassword) success=1;
    if (success) {
        signedup = 1;
        email = document.getElementById("signupemail").value;
        password = document.getElementById("signuppassword").value;
        console.log(email, password);
        changepg2("signup", "signupsuccess");
        curpage = 2;
    }
}

function loginbutton() {
    var success = 1;
    input_email = document.getElementById("loginemail").value;
    input_password = document.getElementById("loginpassword").value;

    if (!(validEmail_login && validPassword_login)) success = 0;
    else if (signedup == 0 || input_email!=email || input_password!=password) {
        success = 0;
        document.getElementById("infologin").style.color = "red";
        document.getElementById("infologin").textContent = "Credential do not match!";
    }

    if (success) {
        document.getElementById("header1").style.display = "none";
        document.getElementById("header2").style.display = "none";
        changepg2("login", "loginsuccess");
    }
}


// input validation change state
// if state = 1, it's valid input, add check icon
// if state = 0, it's invalid input, change box red, view message
function changestate(inputstr, state, messageContent) {
    if (state == 1) {
        document.getElementById(inputstr).style.borderColor = "white";
        document.getElementById(inputstr+"message").style.display = "none";
        document.getElementById(inputstr+"check").style = "color:rgb(67,162,77);position:absolute;margin-left:80%;margin-top:2.5%;";
    }
    else {
        document.getElementById(inputstr).style.borderColor = "red";
        document.getElementById(inputstr+"message").style.display = "block";
        document.getElementById(inputstr+"message").textContent = messageContent;
        document.getElementById(inputstr+"check").style.display = "none";
    }
}

function handleFirstname(str) {
    var inputstr = "signupfirstname";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your first name!");
        validFirstname = 0;
    }
    else if (!(str[0]>='A'&&str[0]<='Z')) {
        changestate(inputstr, 0, "First Character must be capital letter!");
        validFirstname = 0;
    }
    else if (/\d/.test(str)) {
        changestate(inputstr, 0, "First name cannot contain numbers!");
        validFirstname = 0;
    }
    else if (!(/^[A-Z][a-z]*$/.test(str))) {
        changestate(inputstr, 0, "First name cannot contain special letters!");
        validFirstname = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validFirstname = 1;
    }
}

function handleLastname(str) {
    var inputstr = "signuplastname";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your last name!");
        validLastname = 0;
    }
    else if (!(str[0]>='A'&&str[0]<='Z')) {
        changestate(inputstr, 0, "First Character must be capital letter!");
        validLastname = 0;
    }
    else if (/\d/.test(str)) {
        changestate(inputstr, 0, "Last name cannot contain numbers!");
        validLastname = 0;
    }
    else if (!(/^[A-Z][a-z]*$/.test(str))) {
        changestate(inputstr, 0, "Last name cannot contain special letters!");
        validLastname = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validLastname = 1;
    }
}

function handleGender(num) {
    var inputstr = "signupgender";
    var r1 = document.getElementById("signupgender1");
    var r2 = document.getElementById("signupgender2");
    if (r1.checked && r2.checked) {
        if (num==1) r2.checked = false;
        if (num==2) r1.checked = false;
    }
    if (r1.checked || r2.checked) {
        document.getElementById(inputstr+"message").style.display = "none";
        document.getElementById(inputstr+"check").style = "color:rgb(67,162,77);position:absolute;margin-left:80%;margin-top:2.5%;";
        validGender = 1;
    }
    else {
        document.getElementById(inputstr+"message").style.display = "block";
        document.getElementById(inputstr+"message").textContent = "Please select your gender!";
        document.getElementById(inputstr+"check").style.display = "none";
        validGender = 0;
    }
}

function handleEmail(str) {
    var inputstr = "signupemail";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your email!");
        validEmail = 0;
    }
    else if (!(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(str))) {
        changestate(inputstr, 0, "Your email address is invalid!");
        validEmail = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validEmail = 1;
    }
}

function handlePassword(str) {
    var inputstr = "signuppassword";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your password!");
        validPassword = 0;
    }
    else if (str.length < 6 || !(/[A-Z]/.test(str)) || !(/[a-z]/.test(str)) || !(/[a-z]/.test(str)) || !(/[!@#$%^&*(),.?":{}|<>]/.test(str))) {
        changestate(inputstr, 0, "Requirement: at least 6 characters, one capital letter, one lowercase letter, at least one digit and one special\ncharacter!");
        document.getElementById(inputstr+"message").innerHTML = "Requirement: at least 6 characters, one capital letter,<br> one lowercase letter, at least one digit and one special<br>character!";
        validPassword = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validPassword = 1;
    }
}

function handleConfirmPassword(str) {
    var inputstr = "signupconfirmpassword";
    var messageContent = "";
    var pw = document.getElementById("signuppassword").value
    if (str == "") {
        changestate(inputstr, 0, "Please re-enter your password!");
        validConfirmpassword = 0;
    }
    else if (pw != str) {
        changestate(inputstr, 0, "Password does not match!");
        validConfirmpassword = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validConfirmpassword = 1;
    }
}

function handleEmailLogin(str) {
    var inputstr = "loginemail";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your email!");
        validEmail_login = 0;
    }
    else if (!(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(str))) {
        changestate(inputstr, 0, "Your email address is invalid!");
        validEmail_login = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validEmail_login = 1;
    }
}

function handlePasswordLogin(str) {
    var inputstr = "loginpassword";
    var messageContent = "";
    if (str == "") {
        changestate(inputstr, 0, "Please enter your password!");
        validPassword_login = 0;
    }
    else {
        changestate(inputstr, 1, messageContent);
        validPassword_login = 1;
    }
}