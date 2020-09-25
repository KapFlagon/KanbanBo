# KanbanBo
A Kanban board software implementation built in Java and using JavaFX.
## Design
Will begin with a simple implementation, and iterate to add extra functionality as time goes on.
- 3 views.
    - Start view for file selection, and new file creation.
        - Pop-up for file selection.
        - Pop-up for file creation.
    - Board view, containing lanes and cards.
        - Create lane directly in board (only with title text).
        - Create card directly in lane (only with title text).
        - Pop-up for viewing/inputting card details.
    - Archived card view.
        - List of cards which have been archived. 
- Lane design.
    - Title text.
    - List of cards.
- Card design.
    - Title text.
    - Description text.