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
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }
        },
        {
            name: 'Produktivität',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Reise',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Navigation',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Streaming & Bücher',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Foto & Video',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Spiele',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Shopping & Finanzen',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Wetter',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Gesundheit',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        },
        {
            name: 'Sonstiges',
            settings: {
                location: {
                    status: "filtered",
                    filterSettings: {
                        distance: 1
                    }
                },
                contacts: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                internet: {
                    status: "filtered"
                },
                camera: {
                    status: "filtered"
                },
                mic: {
                    status: "filtered"
                },
                environment: {
                    status: "filtered"
                },
                movement: {
                    status: "filtered"
                },
                bluetooth: {
                    status: "filtered"
                },
                nfc: {
                    status: "filtered"
                },
                calls: {
                    status: "filtered"
                },
                sms: {
                    status: "filtered"
                },
                calendar: {
                    status: "filtered",
                    filterSettings: {
                        horizontal: [],
                        vertical: [],
                    }
                },
                accounts: {
                    status: "filtered"
                },
                storage: {
                    status: "filtered"
                },
                identity: {
                    status: "filtered"
                },
                smsHistory: {
                    status: "filtered"
                },
                mmsHistory: {
                    status: "filtered"
                },
                callsHistory: {
                    status: "filtered"
                }
            }

        }
    ],
}