package view;

public class TotalView {
    private final WriterView writerView;
    private final PostView postView;
    private final LabelView labelView;
    private final Integer writerId;
    private final Integer EXIT_NUMBER = 0;
    private final Integer MENU_OPTIONS = 3;
    private final InputManager inputManager;

    public TotalView() {
        inputManager = InputManager.getInstance();
        writerView = new WriterView();
        postView = new PostView();
        labelView = new LabelView();
        writerId = login();
    }

    private Integer login() {
        System.out.println("Do you have an account? [y/n]");
        if(inputManager.getYesOrNo()) {
            var writer = writerView.login();
            System.out.println(writer.getFirstName() + " " + writer.getLastName());
            return writer.getId();
        }
        else {
            return writerView.createWriter().getId();
        }
    }

    public void start() {
        Integer input = EXIT_NUMBER - 1;
        do {
            showMenu();
            input = inputManager.getNumberFromUserBetweenMinAndMax(0,MENU_OPTIONS);
            switch (input) {
                case(1) :
                    postView.userPostsMenu(writerId);
                    break;
                case(2) :
                    writersMenu();
                    break;
                case(3) :
                    postView.showAllPosts();
                    break;
            }

        } while(!input.equals(EXIT_NUMBER));
    }

    private void showMenu() {
        System.out.println("Enter number from menu:");
        System.out.println("  1. Your posts");
        System.out.println("  2. Writers");
        System.out.println("  3. All posts");
        System.out.println("For exit enter \"" + EXIT_NUMBER + "\"");
    }


    private void writersMenu() {
        writerView.showAllWriters();
        System.out.println("Enter writer's id to see writer's post  (0 for back):");
        writerView.showAllWriters();
    }


}
