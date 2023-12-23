// initialize global variable
const correct_account = "1234567890123456";
const correct_pin = "1234";

let account = "";
let pin = "";
let attempt = 5;
let drawval = 0;
let depositval = "";
let transferval = "";

let pnt = 0;
let balance = 2000;
var data = [
    ["Date", "Funds Out", "Funds In", "Running Balance"],
    [Date(), 0, 0, 2000]
];

// function for string pop
function pop(str) {
    ret = str.substring(0, str.length-1);
    return ret;
}
// change page id str1 to str2
function changepg(str1,str2) {
    document.getElementById(str1).style.display = "none";
    document.getElementById(str2).style.display = "block";
}
// get current time string
function get_time() {
    var currentDate = new Date();
    var ret = currentDate.toString();
    return ret;
}
// init table
function init_table() {
    document.getElementById("curbalance").textContent = "Current Balance: $"+"2000";
    let tablebody = document.getElementById("infotable").getElementsByTagName("tbody")[0];
    while (pnt < data.length) {
        var row = tablebody.insertRow(-1);
        for (var j = 0; j < 4; j++) {
            var valueCell = row.insertCell(j);
            valueCell.innerHTML = data[pnt][j];
        }
        pnt++;
    }
    return;
}
// add data
function add_data(str1, str2, str3, str4) {
    var tmp = [str1, str2, str3, str4];
    document.getElementById("curbalance").textContent = "Current Balance: $"+str4;
    data.push(tmp);
    let tablebody = document.getElementById("infotable").getElementsByTagName("tbody")[0];
    while (pnt < data.length) {
        var row = tablebody.insertRow(-1);
        for (var j = 0; j < 4; j++) {
            var valueCell = row.insertCell(j);
            valueCell.innerHTML = data[pnt][j];
        }
        pnt++;
    }
    return;
}
// valid variable of data
function isvalid(str) {
    if (str.length == 0 || str == ".") return false
    else return true
}

// function for page1
function click1(num) {
    let element = document.getElementById("account");
    if (account.length == 0) {
        element.value = "";
        element.type = "password";
        element.style.color = "black";
    }
    element.value += num;
    account = account + num;
}
function click1_enter() {
    init_table();
    let element = document.getElementById("account");
    if (correct_account == account) {
        account = "";
        element.value = "Enter Account Number";
        element.type = "text";
        element.style.color = "gray";
        changepg("page1", "page2");
    } else {
        alert("That account number does not exist!");
        element.value = "Enter Account Number";
        element.type = "text";
        element.style.color = "gray";
        account = "";
    }
}
function click1_del() {
    let element = document.getElementById("account");
    if (account.length > 0) {
        if (account.length == 1) {
            element.value = "Enter Account Number";
            element.style.color = "gray";
            element.type = "text";
        }
        else element.value = pop(element.value);
        account = pop(account);
    }
}

// function for page2
function click2(num) {
    let element = document.getElementById("pin");
    if (pin.length == 0) {
        element.value = "";
        element.type = "password";
        element.style.color = "black";
    }
    element.value += num;
    pin = pin + num;
}
function click2_enter() {
    let element = document.getElementById("pin");
    if (attempt == 0) {
        alert("You tried all attempts. Return Card to try again.");
        return;
    }
    if (correct_pin == pin) {
        pin = "";
        attempt = 5;
        element.value = "Enter PIN";
        element.type = "text";
        element.style.color = "gray";
        changepg("page2", "page3");
    } else {
        attempt--;
        alert("Incorrect PIN. You have " + attempt + " attempts left");
        element.value = "Enter PIN";
        element.type = "text";
        element.style.color = "gray";
        pin = "";
    }
}
function click2_del() {
    let element = document.getElementById("pin");
    if (pin.length > 0) {
        if (pin.length == 1) {
            element.value = "Enter PIN";
            element.style.color = "gray";
            element.type = "text";
        }
        else element.value = pop(element.value);
        pin = pop(pin);
    }
}
function click2_return() {
    let element = document.getElementById("pin");
    pin = "";
    attempt = 5;
    element.value = "Enter PIN";
    element.type = "text";
    element.style.color = "gray";
    changepg("page2", "page1");
}

// function for page3
function click3(num) {
    if (num==1) changepg("page3", "page4");
    if (num==2) changepg("page3", "page5");
    if (num==3) changepg("page3", "page8");
    if (num==4) changepg("page3", "page12");
}
function click3_return() {
    changepg("page3", "page1");
}

// function for page4
function click4_return() {
    changepg("page4", "page3");
}

// function for page5
function setdraw(num) {
    drawval = num;
    document.getElementById("drawq").textContent = "$"+drawval+"?";
    changepg("page5", "page6");
}
function changedraw(num) {
    drawval += num;
    if (drawval < 0) drawval = 0;
    let drawinput = document.getElementById("draw");
    drawinput.value = drawval; 
}
function click5_enter() {
    document.getElementById("drawq").textContent = "$"+drawval+"?";
    changepg("page5", "page6");
}
function click5_return() {
    changepg("page5", "page3");
}

// function for page6
function yesclick() {
    let drawinput = document.getElementById("draw");
    if (balance-drawval < 0) {
        alert("Not Enough Money! Return back.");
    } else {
        add_data(Date(), drawval, 0, balance-drawval);
        balance-=drawval;
        drawval = 0;
        drawinput.value = "0";
        changepg("page6", "page7");
    }
}
function noclick() {
    let drawinput = document.getElementById("draw");
    drawval = 0;
    drawinput.value = "0";
    changepg("page6", "page5");
}

// function for page7
function homeclick() {
    changepg("page7", "page3");
}
function withdrawclick() {
    changepg("page7", "page5");
}
function returncardclick() {
    changepg("page7", "page1");
}

// function for page8
let dotclicked = 0;
function click8(num) {
    let element = document.getElementById("deposit");
    if (depositval.length == 0) {
        element.value = "";
        element.style.color = "black";
    }
    if (num == -1) {
        if (dotclicked == 0) {
            dotclicked = 1;
            document.getElementById("dot").style.backgroundColor = "gray";
            element.value += ".";
            depositval = depositval + ".";
        }
    } else {
        element.value += num;
        depositval = depositval + num;
    }
}
function click8_enter() {
    if (isvalid(depositval)) {
        document.getElementById("dot").style.backgroundColor = "rgb(240,240,240)";
        let element = document.getElementById("deposit");
        document.getElementById("depositq").textContent = "$"+depositval+"?";
        dotclicked = 0;
        element.value = "$0000.00";
        element.style.color = "gray";
        changepg("page8", "page9");
    }
}
function click8_del() {
    let element = document.getElementById("deposit");
    if (depositval.length > 0) {
        if (depositval.length == 1) {
            element.value = "$0000.00";
            element.style.color = "gray";
        }
        else element.value = pop(element.value);
        if (depositval.substring(depositval.length-1, depositval.length) == ".") {
            dotclicked = 0;
            document.getElementById("dot").style.backgroundColor = "rgb(240,240,240)";
        }
        depositval = pop(depositval);
    }
}
function click8_return() {
    let element = document.getElementById("deposit");
    document.getElementById("dot").style.backgroundColor = "rgb(240,240,240)";
    element.value = "$0000.00";
    element.style.color = "gray";
    depositval = "";
    dotclicked = 0;
    changepg("page8", "page3");
}

// function for page9
function yesclick2() {
    changepg("page9", "page10");
}
function noclick2() {
    depositval = "";
    changepg("page9", "page8");
}
// function for page10
function okclick() {
    add_data(Date(), 0, parseFloat(depositval), balance+parseFloat(depositval));
    balance+=parseFloat(depositval);
    dotclicked = 0;
    depositval = "";
    changepg("page10", "page11");
}
// function for page11
function homeclick2() {
    changepg("page11", "page3");
}
function withdrawclick2() {
    changepg("page11", "page8");
}
function returncardclick2() {
    changepg("page11", "page1");
}

// function for page12
let dotclicked2 = 0;
function click12(num) {
    let element = document.getElementById("transfer");
    if (transferval.length == 0) {
        element.value = "";
        element.style.color = "black";
    }
    if (num == -1) {
        if (dotclicked2 == 0) {
            dotclicked2 = 1;
            document.getElementById("dot2").style.backgroundColor = "gray";
            element.value += ".";
            transferval = transferval + ".";
        }
    } else {
        element.value += num;
        transferval = transferval + num;
    }
}
function click12_enter() {
    if (document.getElementById("from").value != "-1" && document.getElementById("to").value != "-1" && isvalid(transferval)) {
        document.getElementById("dot2").style.backgroundColor = "rgb(240,240,240)";
        let element = document.getElementById("transfer");
        document.getElementById("transferq").textContent = "$"+transferval;
        dotclicked2 = 0;
        element.value = "$0000.00";
        element.style.color = "gray";
        document.getElementById("from").value = "-1";
        document.getElementById("to").value = "-1";
        changepg("page12", "page13");
    }
}
function click12_del() {
    let element = document.getElementById("transfer");
    if (transferval.length > 0) {
        if (transferval.length == 1) {
            element.value = "$0000.00";
            element.style.color = "gray";
        }
        else element.value = pop(element.value);
        if (transferval.substring(transferval.length-1, transferval.length) == ".") {
            dotclicked2 = 0;
            document.getElementById("dot2").style.backgroundColor = "rgb(240,240,240)";
        }
        transferval = pop(transferval);
    }
}
function click12_return() {
    let element = document.getElementById("transfer");
    document.getElementById("dot2").style.backgroundColor = "rgb(240,240,240)";
    element.value = "$0000.00";
    element.style.color = "gray";
    transferval = "";
    dotclicked2 = 0;
    document.getElementById("from").value = "-1";
    document.getElementById("to").value = "-1";
    changepg("page12", "page3");
}
// function for page13
function yesclick3() {
    if (balance-parseFloat(transferval) < 0) {
        alert("Not Enough Money! Return back.");
    } else {
        add_data(Date(), parseFloat(transferval), 0, balance-parseFloat(transferval));
        balance-=parseFloat(transferval);
        dotclicked2 = 0;
        transferval = "";
        changepg("page13", "page14");
    }
}
function noclick3() {
    transferval = "";
    dotclicked2 = 0;
    changepg("page13", "page12");
}
// function for page14
function homeclick3() {
    changepg("page14", "page3");
}
function withdrawclick3() {
    changepg("page14", "page12");
}
function returncardclick3() {
    changepg("page14", "page1");
}