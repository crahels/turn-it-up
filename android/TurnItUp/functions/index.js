const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendRegisterNotification = functions.database.ref('/users/{uid}').onCreate(event => {
	
	const getDeviceTokenPromise = event.data.val();
	const getUserProfilePromise = admin.database().ref('/users').once('value');
	
	return Promise.all([getDeviceTokenPromise, getUserProfilePromise]).then(results => {
		const tokensSnapshot = results[0];
		var displayName = tokensSnapshot.username;

		const payload = {
			notification: {
				title: 'Welcome to Turn It Up!',
				body: 'Hey ' + displayName + ', Thanks for signing up!'
			}
		};
		
		var tokens = tokensSnapshot.notificationToken;
		
		return admin.messaging().sendToDevice(tokens, payload).then(response => {
			const tokensToRemove = [];
			response.results.forEach((result, index) => {
				const error = result.error;
				if (error) {
					console.error('Failure sending notification to', tokens[index], error);
					if (error.code === 'messaging/invalid-registration-token' || error.code === 'messaging/registration-token-not-registered') {
						tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
					}
				}
			});
			return Promise.all(tokensToRemove);
		});
	});
});
