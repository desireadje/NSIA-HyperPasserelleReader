

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
}

function numberWithCommas(x) {
	// Permet d'arrondir
    return (Math.round(x)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    //return (x.toFixed(0)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
