# Persistence
Management of app state (possibly integrating feature-specific states), persistence (storing state to some kind of database), and sharing data with AvareMonitor

# Comments to App_Settings.json

First draft of data model 
// Todo:
// Nutzergeneriertes Passwort um Schlüssel zu sichern?
// Verschlüsselung mit Android-Boardmitteln
// salted
// Daten in JSON-Datei speichern

{ 
  "_id": 1, // package name?
  // "name": "WhatsApp",
  "category_id": 2,
  "settings": {
    "location": {
      "status": "filtered", // möglich: "blocked", "empty", "replaced", "filtered", "enabled" oder: "inherit"
      "blockSettings": {
        "blockTime": "2018-06-14T15:05:30", // Zeit des unwirksam werdens
        "duration": 3600
      },
      "filterSettings": {
        "distance": 50
      }
    },
    "contacts": {
      "status": "filtered",
      "filterSettings": {
        "horizontal": [
          "Fritsch", // sinnvolle ID? // custom fields?
          "company='AIFB'" //möglich? erwünscht?
        ],
        "vertical": [
          "lastName",
          "company"
        ]
      }
    },
    "internet": {
      "status": "blocked",
      "blockSettings": {
        "blockTime": "2018-06-14T15:05:30",
        "duration": 3600
      },
      "filterSettings": {
        "filterIP": [
          "192.160.1.1",
          "123.456.7.8"
        ]
      }
    },
    "camera": {
      "status": "blocked"
    },
    "mic": {
      "status": "enabled"
    },
    "environment": "blocked", // etc. wie oben
    "movement": "blocked",
    "bluetooth": "enabled",
    "nfc": "blocked",
    "calls": "enabled",
    "sms": "blocked",
    "calender": "blocked", //TODO
    "accounts": "filtered",
    "storage": "filtered",
    "identity": "blocked",
    "smsHistory": "blocked",
    "mmsHistory": "blocked",
    "callsHistory": "blocked"
  }
}
