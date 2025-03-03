# quick cook book : 
- suppose I want to make an **OrangeTrigger** class


1. create the orange class 
2. implement the checkTrigger method without worrying about where or how often to check it, just implement the condition of the trigger `such that the function check_trigger returns true only if the condition of the trigger is met`
3. write `@JsonTypeName("OrangeTrigger")` in the class file 
4. add ```json @JsonSubTypes.Type(value = OrangeTrigger.class, name = "OrangeTrigger")``` to the ITrigger class
5. add **setters and getters** for every variable that needs to be stored in the json file
6. add an **Empty constructor**
7. add the variable names as is without spaces in the RuleAdder class (you will note where to put it trust me)
8. add build method to the TriggerBuilder, and add an if statement to handel it (remember that the builder should `validate the inputs` and return an object of Type OrangeTrigger)
9. صل على النبي

---

- put your work in the accreditation section
- if any of your work needs to be documented, please don't be lazy 
- don't comment bad code REWRITE IT
- comments are a necessary evil, so don't over do it
## remember to test your code, and to clean it 
## please, remember to avoid duplicate code 