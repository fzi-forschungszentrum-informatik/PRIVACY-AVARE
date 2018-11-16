/*
        Copyright 2016-2018 AVARE project team

        AVARE-Project was financed by the Baden-Württemberg Stiftung gGmbH (www.bwstiftung.de).
        Project partners are FZI Forschungszentrum Informatik am Karlsruher
        Institut für Technologie (www.fzi.de) and Karlsruher
        Institut für Technologie (www.kit.edu).

        Files under this folder (and the subfolders) with "Created by AVARE Project ..."-Notice
	    are our work and licensed under Apache Licence, Version 2.0"

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
//TODO: elaborate settings, implement default preferences as defined in AVARE Project
export default initialPreferences = {
    apps: {
        location: {
            status: "inherit",
            filterSettings: {
                distance: 1
            }

        },
        contacts: {
            status: "inherit",
            filterSettings: {
                horizontal: [],
                vertical: [],
            }
        },
        internet: {
            status: "inherit"
        },
        camera: {
            status: "inherit"
        },
        mic: {
            status: "inherit"
        },
        environment: {
            status: "inherit"
        },
        movement: {
            status: "inherit"
        },
        bluetooth: {
            status: "inherit"
        },
        nfc: {
            status: "inherit"
        },
        calls: {
            status: "inherit"
        },
        sms: {
            status: "inherit"
        },
        calendar: {
            status: "inherit",
            filterSettings: {
                horizontal: [],
                vertical: [],
            }
        },
        accounts: {
            status: "inherit"
        },
        storage: {
            status: "inherit"
        },
        identity: {
            status: "inherit"
        },
        smsHistory: {
            status: "inherit"
        },
        mmsHistory: {
            status: "inherit"
        },
        callsHistory: {
            status: "inherit"
        }
    },
    categories: [
        {
            name: 'Soziale Netzwerke & Kommunikation',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "enabled",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "enabled"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "enabled"
                },
                storage: {
                    status: "enabled"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }
        },
        {
            name: 'Produktivität',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "enabled",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "enabled"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Reise',
            settings: {
                location: {
                    status: "enabled",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Navigation',
            settings: {
                location: {
                    status: "enabled",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "enabled"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "enabled"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Streaming & Bücher',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "enabled"
                },
                storage: {
                    status: "enabled"
                },
                identity: {
                    status: "enabled"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Foto & Video',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "blocked"
                },
                camera: {
                    status: "enabled"
                },
                mic: {
                    status: "enabled"
                },
                environment: {
                    status: "enabled"
                },
                movement: {
                    status: "enabled"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "enabled"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Spiele',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Shopping & Finanzen',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "enabled"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "enabled"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Wetter',
            settings: {
                location: {
                    status: "enabled",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "enabled"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Sport & Gesundheit',
            settings: {
                location: {
                    status: "enabled",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "blocked"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "enabled"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "enabled"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        },
        {
            name: 'Sonstiges',
            settings: {
                location: {
                    status: "blocked",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "blocked"
                },
                camera: {
                    status: "blocked"
                },
                mic: {
                    status: "blocked"
                },
                environment: {
                    status: "blocked"
                },
                movement: {
                    status: "blocked"
                },
                bluetooth: {
                    status: "blocked"
                },
                nfc: {
                    status: "blocked"
                },
                calls: {
                    status: "blocked"
                },
                sms: {
                    status: "blocked"
                },
                calendar: {
                    status: "blocked",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "blocked"
                },
                storage: {
                    status: "blocked"
                },
                identity: {
                    status: "blocked"
                },
                smsHistory: {
                    status: "blocked"
                },
                mmsHistory: {
                    status: "blocked"
                },
                callsHistory: {
                    status: "blocked"
                }
            }

        }
    ],
}