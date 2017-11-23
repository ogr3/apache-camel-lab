##Lab 2 - Message Translator ##

Hur kan system som använder olika dataformat kommunicera med varandra? 

**Problem:** Olika system använder sina egna dataformat. API:erna på open-notify.org kommunicerar med JSON så vi behöver konvertera meddelandet från till ett dataformat som vår applikation stödjer.

**Lösning:** Använd en ‘message translator’, mellan applikationerna och transformera svaret från JSON till OpenNotifyIssPositionBean

**Testa att lösa övningen**

 * med en processor
 * med en böna
 * genom att referera processorn/bönan på olika sätt
 * genom att specificera vilken metod som ska anropas på processorn/bönan
