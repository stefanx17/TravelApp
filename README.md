# TravelApp
A simple app for getting info about travel destinations

Structuri si clase:
Pentru implementarea ierarhiei: tara->judet->oras am ales sa creeze cate o
  clasa pentru fiecare dintre acestea, legaturile implementandu-le astfel:
  - un obiect "parinte" va avea o lista cu toti copii acestuia
  - un copil va avea o referinta la parinte
  (legaturi de tipul one-to-many)
  Acestea ierarhie ar fi astfel relativ usor de modificat fiind nevoie doar de 
  modificarea acelor referinte.
Am ales si implementarea unei clase Agency care sa fie un fel de baza de date 
pentru aplicatie, avand referinte la toate obiectele utilizate(folosinf hashmaps)

Pentru populare a listelor am ales sa folosesc doua fisiere de intrare:
- unul pentru citirea si crearea legaturilor de tipul oras-judet-tara
- unul pentru citirea tuturor locurilor de vacanta

Pentru testare am creat o metoda ce preia input de la Stdin si executa acea
comanda introdusa de utilizator: getInfo, getTop5 etc.
Comenzi posibile si formatul asteptat: 
1) info placeName
2) top[City/County/Country] cityName/countyName/countryName checkIn(dd/mm/yyyy) checkOut(dd/mm/yyyy)
3) cheapest
4) exit
