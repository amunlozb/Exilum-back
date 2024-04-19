# Exilum

#### Curso Escolar 2023-2024
#### Autor: [Ángel Muñoz Lozano](https://github.com/amunlozb/)
#### Tutor: [Juan Pablo Domínguez Mayo](https://github.com/profeinformatica101)
#### Fecha de Inicio: 25-03-2024
#### Fecha de Finalización: 21-06-2024

## Breve descripción del proyecto

La propuesta de valor de este proyecto reside en la simplificación del proceso de realizar planificaciones económicas en el videojuego  
**Path of Exile**, el cuál incluye un mercado que oscila constantemente, y al que se aplican todos los principios de la economía.

**Exilum** facilita el acceso a estos cálculos a una mayor parte de la comunidad, consiguiendo así que un mayor número de usuarios tenga la oportunidad de contribuir al conocimiento y desarrollo de la economía general.

## Definir el objetivo de la aplicación  
**Tener una gran idea** o encontrar un punto del mercado al que no se esté dando un producto o servicio es el punto de partida en cada nuevo proyecto. Antes de comenzar debes **definir claramente el propósito y la misión de la aplicación web**:

- **¿Qué va a hacer la aplicación?**  
 Exilum es una aplicación web que ayuda a los usuarios de Path of Exile a tomar decisiones económicas informadas, además de ayudar con la planificación de diferentes estrategias.
    
- **¿Cuál es su atractivo principal?**  
 El atractivo principal de Exilum es su facilidad de uso. La aplicación está diseñada para ser intuitiva y fácil de navegar, incluso para los usuarios que no están familiarizados con los conceptos económicos.
     
- **¿Qué problema concreto va a resolver?**  
 Exilum resuelve el problema de que los usuarios de Path of Exile a menudo toman decisiones económicas basadas en información errónea o desactualizada. Esto puede provocar una pérdida de recursos y una progresión más lenta en el juego.
      
- **¿Qué necesidad va a cubrir?**  
 Exilum cubre la necesidad de una herramienta fiable y fácil de usar que pueda ayudar a los usuarios de Path of Exile a tomar decisiones económicas informadas y fiables con datos que se actualizan en tiempo real. Esto puede ayudar a los usuarios a mejorar su experiencia de juego y progresar más rápido. 

## Estructura del Proyecto

Al utilizar tecnologías diferentes para cada parte de la aplicación, se ha optado por separar el proyecto en dos repositorios diferentes:

[**Exilum-front**](https://github.com/amunlozb/Exilum-front/):  
- Repositorio para el front-end. Se utiliza el framework **React** para crear una interfaz de usuario moderna y atractiva, utilizando la ayuda de librerias como [Flowbite](https://flowbite.com/) para implementar algunos componentes prefabricados (aunque la mayoría han tenido que ser codificados a mano), y [Tailwind CSS](https://tailwindcss.com/) como alternativa a *Bootstrap* para el diseño responsive, así como la configuración del tema personalizado.  
  
- Este repositorio se ha configurado para realizar pruebas automáticamente en cada commit antes de realizar un empaquetamiento de la aplicación, y por último se lleva a cabo el despliegue a la nube de forma automática
  

[**Exilum-back**](https://github.com/amunlozb/Exilum-back/):  
- Repositorio para el back-end. Se utiliza el framework **Spring** (basado en Java) para gestionar la lógica de negocio y la interacción con la base de datos
