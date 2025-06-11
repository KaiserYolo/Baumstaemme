// src/utils/api.js

export const fetchMapData = async () => {
    // Simuliert einen API-Call
    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                tiles: [
                    { id: 1, x: 5, y: 5, type: 'Gras' },
                    { id: 2, x: 6, y: 5, type: 'Wasser' },
                    { id: 3, x: 7, y: 5, type: 'Gras' },
                    { id: 4, x: 6, y: 6, type: 'Wasser' },
                ],
                assets: {
                    gras: '/assets/wald.png', // Passe die Pfade bei Bedarf an
                    wasser: '/assets/test_dorf.png', // Passe die Pfade bei Bedarf an
                }
            });
        }, 500); // Simuliert eine Netzwerkverzögerung
    });
};