# NASM-GUI
A Java project.

## Function
NASM using JavaFX.
In formatting templates,used String.format() to let the code be more precise.
Using JavaFX 11 with JDK 19.

## Todo
### Classes
- [x] UserSubSystem
- [x] TeamFormatter
- [x] PlainTextTeamFormatter
- [x] HTMLTeamFormatter
- [x] XMLTeamFormatter
- [x] TeamParser
### Layouts
- [x] RootLayout
- [x] TeamOverview

### Process
- [x] Build classes
- [x] Design Layouts
- [x] Test
- [x] Write Javadoc

### Notes
1. A method to show only the team names on teamComboBox.
    ```java
     //设置下拉框中每个选项的显示内容，只显示团队名
        teamComboBox.setCellFactory(new Callback<ListView<Team>, ListCell<Team>>() {
            @Override
            public ListCell<Team> call(ListView<Team> param) {
                return new ListCell<Team>() {
                    @Override
                    protected void updateItem(Team item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTeamName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
```
