# Avito trainee weather application
Android приложение погоды для [тестового задания](https://github.com/avito-tech/android-trainee-task-2021) avito-tech

<img src="https://media.giphy.com/media/4Mh8qPJxJMpf0q7v2Y/giphy.gif" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384014-c31c233c-52c7-44e4-a26a-31090bcceb05.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384036-88836748-ff4b-4f5f-a940-969bc967b138.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384063-ef46f76a-5cae-4094-af3c-8867415384ba.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384087-74e14835-3632-47c6-af3a-7e1d0cc95bc9.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384099-14e31c3a-4767-43f9-98fc-aeb6e8ed03d2.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384117-cc242c73-8ea5-4b0b-a924-23250b7ad206.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384131-2bb48b92-f0e6-4238-bbbb-2dc438af190d.jpg" width="180"/><img src="https://user-images.githubusercontent.com/33917440/197384146-e0007b45-d3a8-4f94-bd13-b5e5dde54908.jpg" width="180"/>





## Возможности
* Отображение погоды в текущей локации по координатам
* Отображение погоды в любом городе мира
* Поиск городов из списка с сервера
* Сохранение города локально на устройстве
* Отображение прогноза погоды за неделю (на самом деле 3 дня из-за ограничений api)

## Технологии
* Kotlin
* Architecture components
* MVVM
* Navigation
* Coroutines
* Room
* Retrofit + Moshi
* Dagger hilt
* Recycler view
* View binding
* LiveData + Flow
* Coil
* Material components
* Easy permissions
* Location services

Приложение использует api [weather.api.com](https://www.weatherapi.com/)

Для запуска проекта необходимо получить ключ и поместить его в local.properties в виде API_KEY = ВАШ_КЛЮЧ
