debugger // Fn + F10 pour debugger
$('#smsRecu').html(0);
$('#smsDelivered').html(0);
$('#solde').html(0);
$('#consommation').html(0);

// 1- je récupère les sms reçus du jour par tous compte
findAllSmsGetTodayByClient();
// 2- je récupère les sms délivrés du jour par tous compte
findAllSmsDeliveredByClient();
// 3- je récupère le solde par client
findConsoYesterdayByClient();
// 4- je récupère le consommation par compte
findConsoTodayByClient();

// Cette fonction permet de récupèrer et d'afficher les information du compte
// sur le table d'admin du client
function getCompteInformations(sel) {
	debugger // Fn + F10 pour debugger
	// je récupère le compte sélectionné
	var compte = sel.value;

	if (compte == "All") {

		// 1- je récupère les sms reçus du jour par tous compte
		findAllSmsGetTodayByClient();
		// 2- je récupère les sms délivrés du jour par tous compte
		findAllSmsDeliveredByClient();
		// 3- je récupère le solde par client
		findConsoYesterdayByClient();
		// 4- je récupère le consommation par compte
		findConsoTodayByClient();

	} else {
		// 1- je récupère les sms reçu du jour par compte
		findSmsGetTodayByCompte(compte);
		// 2- je récupère les sms délivrés du jour par compte
		findSmsDeliveredByCompte(compte);
		// 3- je récupère le solde par compte
		findConsoYesterdayByCompte(compte);
		// 4- je récupère le consommation par compte
		findConsoTodayByCompte(compte);
	}

}

// Cette requête retourne le nomnbre de sms reçu par hyperaccess aujourd'hui
function findSmsGetTodayByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		// url : "http://localhost:80/smsapi/findSmsGetTodayByCompte/" + compte,
		url : "http://localhost/smsapi/findSmsGetTodayByCompte/" + compte,
		type : 'GET',
		dataType : 'json',
		success : function(json) { 
			var jsonData = json;
			$('#smsRecu').html(numberWithCommas(jsonData));

		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});

}

// Cette requête retourne le nomnbre de sms reçu par hyperaccess aujourd'hui
function findSmsDeliveredByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findSmsDeliveredByCompte/" + compte,
		type : 'GET',
		dataType : 'json',
		success : function(json) {

			var jsonData = json;
			$('#smsDelivered').html(numberWithCommas(jsonData));
		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});

}

// Cette requête retourne le solde du compte sélectioné
function findConsoYesterdayByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findConsoYesterdayByCompte/" + compte,
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			var jsonData = json;
			$('#solde').html(numberWithCommas(jsonData));

		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});

}

// Cette requête retourne le solde du compte sélectioné
function findConsoTodayByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findConsoTodayByCompte/" + compte,
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			var jsonData = json;
			$('#consommation').html(numberWithCommas(jsonData));

			
		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}

// Cette requête retourne le nomnbre de sms reçu par hyperaccess
// aujourd'huifindAllSmsGetTodayByClient
function findAllSmsGetTodayByClient() {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findAllSmsGetTodayByClient",
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			var jsonData = json;
			$('#smsRecu').html(numberWithCommas(jsonData));

		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}

// Cette requête retourne le nomnbre de sms delivrés par hyperaccess aujourd'hui
function findAllSmsDeliveredByClient() {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findAllSmsDeliveredByClient",
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			var jsonData = json;
			$('#smsDelivered').html(numberWithCommas(jsonData));

		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}

// Cette requête retourne le solde du client sélectioné
function findConsoYesterdayByClient(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findConsoYesterdayByClient",
		type : 'GET',
		dataType : 'json',
		success : function(json) {

			var jsonData = json;
			$('#solde').html(numberWithCommas(jsonData));

		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}

// Cette requête retourne le solde du compte sélectioné
function findConsoTodayByClient() {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findConsoTodayByClient",
		type : 'GET',
		dataType : 'json',
		success : function(json) {
			//alert(json);
			var jsonData = json;
			$('#consommation').html(numberWithCommas(jsonData));
		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
}

function numberWithCommas(x) {
	// Permet d'arrondir
    return (Math.round(x)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    //return (x.toFixed(0)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}