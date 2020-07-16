$('#smsRecu').html(0);
$('#smsDelivered').html(0);
$('#solde').html(0);
$('#consommation').html(0);


// Cette fonction permet de récupérer les comptes d'un client
function findCompteByClientUsername() {
	debugger;
	var username = $('#client').val();
	
	if(username =='All'){
		var champSelectCompte = document.getElementById("compte");
		champSelectCompte.innerHTML = null;
		champSelectCompte.options[0] = new Option(
				'Tous les comptes', '');
		return
	}

	$.ajax({
		url : "http://localhost/smsapi/findCompteByClientUsername/" + username,
		type : 'GET',
		dataType : 'json',
		success : function(json) {

			var jsonData = json;
			var champSelectCompte = document.getElementById("compte");

			

			if (jsonData.length > 0) {
				var reponse = jsonData;
				
				//console.log(JSON.stringify(reponse));

				champSelectCompte.innerHTML = null;
				champSelectCompte.options[0] = new Option(
						'Tous les comptes', '');
				var optlistCpt = champSelectCompte.options.length;

				for ( var key in reponse) {
					var compte = reponse[key];

					champSelectCompte.options[optlistCpt] = new Option(
							compte.compte, compte.compte);
					optlistCpt = optlistCpt + 1;
				}
			} else {
				// var champSelectQuartier =
				// document.getElementById("ID_QUARTIER_UPDATE");
				champSelectCompte.innerHTML = null;
				champSelectCompte.options[0] = new Option(
						'Tous les comptes', '');
			}
		},
		error : function(data, status, er) {
			console.log(data);
			console.log(status);
			console.log(er);
		}
	});
	
	
	// je recupère le username du client
	var username = $('#client').val();
	// alert('username : '+ username);
	
	// 1- je récupère les sms reçus du jour par tous compte
	findAllSmsGetTodayByClient(username);
	// 2- je récupère les sms délivrés du jour par tous compte
	findAllSmsDeliveredByClient(username);
	// 3- je récupère le solde par client
	findSoldeByClient(username);
	// 4- je récupère le consommation par compte
	findConsommationByClient(username);
}



// Cette fonction permet de récupèrer et d'afficher les information du compte
// sur le table d'admin du client
function getCompteInformations() {
		
	debugger // Fn + F10 pour debugger
	// je récupère le compte sélectionné
	var compte = $('#compte').val();
	
	
	// alert('compte : '+ compte);
	

	if (compte == "") {
		
		// je recupère le username du client
		var username = $('#client').val();
		// alert('username : '+ username);
		
		// 1- je récupère les sms reçus du jour par tous compte
		findAllSmsGetTodayByClient(username);
		// 2- je récupère les sms délivrés du jour par tous compte
		findAllSmsDeliveredByClient(username);
		// 3- je récupère le solde par client
		findSoldeByClient(username);
		// 4- je récupère le consommation par compte
		findConsommationByClient(username);

	} else {
		
		// 1- je récupère les sms reçu du jour par compte
		findSmsGetTodayByCompte(compte);
		// 2- je récupère les sms délivrés du jour par compte
		findSmsDeliveredByCompte(compte);
		// 3- je récupère le solde par compte
		findSoldeByCompte(compte);
		// 4- je récupère le consommation par compte
		findConsommationByCompte(compte);
	}

}




// Cette requête retourne le nomnbre de sms reçu par hyperaccess aujourd'hui
function findSmsGetTodayByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		// url :
		// "http://localhost:80/smsapi/findSmsGetTodayByCompte/" +
		// compte,
		url : "http://localhost/smsapi/findSmsGetTodayByCompte_commercial/" + compte,
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
	$
			.ajax({
				url : "http://localhost/smsapi/findSmsDeliveredByCompte_commercial/"
						+ compte,
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
function findSoldeByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findSoldeByCompte_commercial/" + compte,
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
function findConsommationByCompte(compte) {
	debugger // Fn + F10 pour debugger
	$.ajax({
				url : "http://localhost/smsapi/findConsommationByCompte_commercial/"
						+ compte,
				type : 'GET',
				dataType : 'json',
				success : function(json) {

					var jsonData = json;
					$('#consommation').html(numberWithCommas(jsonData));

					// alert(JSON.stringify(jsonData));
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
function findAllSmsGetTodayByClient(username) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findAllSmsGetTodayByClient_commercial/"+username,
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
function findAllSmsDeliveredByClient(username) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findAllSmsDeliveredByClient_commercial/"+username,
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
function findSoldeByClient(username) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findSoldeByClient_commercial/"+username,
		type : 'GET',
		dataType : 'json',
		success : function(json) {

			var jsonData = json;
			//alert(jsonData);
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
function findConsommationByClient(username) {
	debugger // Fn + F10 pour debugger
	$.ajax({
		url : "http://localhost/smsapi/findConsommationByClient_commercial/"+username,
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

function numberWithCommas(x) {
	// Permet d'arrondir
    return (Math.round(x)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    //return (x.toFixed(0)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}