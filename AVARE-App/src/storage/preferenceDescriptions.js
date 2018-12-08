export let settingsSaved = [
  {
    key: 'contacts', // contacts in JSON
    name: 'Kontakte',
    description: 'Zugriff auf gespeicherte Telefonnummern und Adressen.',
    icon: 'contacts',
    implemented: true
  },
  {
    key: 'calendar', // calendar in JSON
    name: 'Kalender',
    description: 'Zugriff auf gespeicherte Kalender und Termine',
    icon: 'event',
    implemented: false
  },
  {
    key: 'storage', // storage in JSON
    name: 'Eigene Dateien',
    description: 'Zugriff auf gespeicherte Dokumente, Bilder und Videos.',
    icon: 'description',
    implemented: false
  },
  {
    key: 'smsHistory', // TODO: smsHistory, mmsHistory and callsHistory in JSON
    name: 'Gespeicherte Nachrichten',
    description: 'Zugriff auf gespeicherte SMS, MMS und den Anrufverlauf',
    icon: 'message',
    implemented: false
  }
]
export let settingsLive = [
  {
    key: 'location', // location in JSON
    name: 'Standort',
    description: 'Deinen aktuellen Standort verfolgen.',
    icon: 'location-on',
    implemented: false,
  },
  {
    key: 'camera', // TODO: camera and mic in JSON
    name: 'Kamera und Mikrofon',
    description: 'Bild und Ton aufnehmen.',
    icon: 'camera',
    implemented: false,
  },
  {
    key: 'internet', // Todo: internet, bluetooth and nfc in JSON
    name: 'Datenübertragung',
    description: 'Über das Internet und mit anderen Geräten kommunizieren.',
    icon: 'sync',
    implemented: false
  },
  {
    key: 'calls', // Todo: calls, sms in JSON
    name: 'Kommunikation',
    description: 'Senden/Empfangen von SMS und Telefonanrufen.',
    icon: 'call',
    implemented:  false
  },
  {
    key: 'environment', // TODO: environment, movement and body in JSON
    name: 'Sensoren',
    description: 'Bewegungen des Geräts, Umgebungsdaten und Körperdaten.',
    icon: 'rss-feed',
    implemented: false
  },
]
export let settingsIdentity = [
  {
    key: 'identity', // identity in JSON
    name: 'Identifikatoren',
    description: 'Zugriff auf Informationen, die dich eindeutig identifizieren.',
    icon: 'person',
    implemented: false
  },
  {
    key: 'accounts', // accounts in JSON
    name: 'Accounts',
    description: 'Zugriff auf deine E-Mail-Accounts und Logins.',
    icon: 'vpn-key',
    implemented: false
  },
]

export let states ={
  blocked: 'Blockiert',
  filtered: 'Gefiltert',
  enabled: 'Freigegeben'
}