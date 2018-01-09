# Simulation à base de réseau de pétri pour le Cloud Computing
## Consignes 
**Phase 1** - Dans la première phase nous vous demandons d’implémenter les fonctionnalités
demandées, sans interface graphique, c’est-à-dire :
* définir le fichier d’entrée qui permet de décrire les services, leur réseau de pétri interne et
les relations entre les services 
* un parseur de ce fichier d’entrée 
* concevoir les fonctions d’exécution du système en suivant les règles évoquées plus haut 

**Phase 2** - Juste avant la deuxième phase vous allez interchanger vos codes. Le deuxième phase
consiste à implémenter une interface graphique qui permet :
1. de concevoir une application comme celle présentée sur la Figure 1, et écrire le fichier correspondant

2. le simulateur graphique du déploiement de l’application créée.
C’est pour implémenter le simulateur que le temps d’exécution associé aux transitions est utile.
Il faut que l’interface graphique montre en temps réel et dynamiquement le déroulement du dé-
ploiement en suivant les règles évoquées plus haut, donc en utilisant les fonctionnalités codées
dans la phase 1.
Chacun d’entre vous va utiliser le code de la phase 1 d’un autre pour implémenter la phase
2, donc bien évidemment il faut faire en sorte de donner un code propre, bien structuré et bien
commenté à votre camarade de classe.

## Indications
* Sélectionner le fichier d'entrée dans l'explorateur
* Cliquer sur "Lancer" pour lancer la simulation
* les états en vert sont les états validés
* le texte en noir, à droite des transitions correspond au nom de l'état ou de la transition
* le texte en rouge à droite de certaines transitions correspond à sa dépendance

## Outils
**IDE :** [Netbeans 8.2](https://netbeans.org/downloads/)

**YAML Parser :** [yamlbeans](https://github.com/EsotericSoftware/yamlbeans)

**Gestion des Dépendances :** [Maven](https://maven.apache.org/)

**Affichage des graphes :** [GraphStream](http://graphstream-project.org/)

## Documentation
La javadoc du projet est accessible [ici]( https://elioy.github.io/ACDC_mini_projet_v2/)

## Exemple de fichier d'entrée
    services :
        - &s1
          nom : service 1
          etats :
              - &s1e1
                nom : s1e1
                token : 0
              - &s1e2
                nom : s1e2
                token : 1
              - &s1e3
                nom : s1e3
                token : 0
              - &s1e4
                nom : s1e4
                token : 0
              - &s1e5
                nom : s1e5
                token : 0
          transitions :
              - &s1t1
                nom : s1t1
                duree : 4000
                parents :
                      - *s1e1
                enfants :
                      - *s1e2
              - &s1t2
                nom : s1t2
                duree : 3000
                parents :
                      - *s1e2
                enfants :
                      - *s1e3
              - &s1t3
                nom : s1t3
                duree : 1500
                parents : 
                      - *s1e2
                enfants:
                      - *s1e4
              - &s1t4
                nom : s1t4
                duree : 4000
                parents : 
                      - *s1e3
                      - *s1e4
                enfants :
                      - *s1e5
        - &s2
          nom : service 2
          etats :
              - &s2e1
                nom : s2e1
                token : 1
              - &s2e2
                nom : s2e2
                token : 0
              - &s2e3
                nom : s2e3
                token : 0
              - &s2e4
                nom : s2e4
                token : 0
              - &s2e5
                nom : s2e5
                token : 0
          transitions :
              - &s2t1 
                nom : s2t1
                duree : 4000
                parents :
                    - *s2e1
                enfants :
                    - *s2e2
              - &s2t2 
                duree : 4000
                nom : s2t2
                parents :
                    - *s2e2
                enfants :
                    - *s2e3
              - &s2t3 
                duree : 4000
                nom : s2t3
                parents : 
                    - *s2e2 
                enfants :
                    - *s2e4
              - &s2t4 
                duree : 4000
                nom : s2t4
                parents :
                    - *s2e4
                enfants :
                    - *s2e5
        - &s3
          nom : service 3
          etats :
              - &s3e1
                nom : s3e1
                token : 1
              - &s3e2
                nom : s3e2
                token : 0
              - &s3e3
                nom : s3e3
                token : 0
              - &s3e4
                nom : s3e4
                token : 0
              - &s3e5
                nom : s3e5
                token : 0
              - &s3e6
                nom : s3e6
                token : 0    
          transitions :
              - &s3t1 
                nom : s3t1
                duree : 4000
                parents :
                    - *s3e1
                enfants :
                    - *s3e2
              - &s3t2 
                nom : s3t2
                duree : 4000
                parents :
                    - *s3e2
                enfants :
                    - *s3e3
              - &s3t3 
                nom : s3t3
                duree : 4000
                parents :
                    - *s3e2
                enfants :
                    - *s3e4
              - &s3t4
                nom : s3t4
                duree : 4000
                parents :
                    - *s3e2
                enfants :
                    - *s3e5
              - &s3t5 
                nom : s3t5
                duree : 4000
                parents :
                    - *s3e4
                    - *s3e5
                enfants :
                    - *s3e6
    dependancesExternes :
        - pere : *s1e4
          fils : *s2t2
        - pere : *s2e4
          fils : *s3t1
        - pere : *s2e5
          fils : *s3t5
