package org.example;

public class TicTacToeGame {
    private PlayerType player1Type;
    private PlayerType player2Type;
    private PlayerTurn playerTurn;
    private GameStatus gameStatus;
    private TicTacToeGrid ticTacToeGrid;
    private Controller controller;

    private Cell X_Cell;
    private Cell O_Cell;

    public TicTacToeGame(PlayerType player1Type, PlayerType player2Type, Controller controller) {
        this.player1Type = player1Type;
        this.player2Type = player2Type;
        this.playerTurn = PlayerTurn.X;
        this.ticTacToeGrid = new TicTacToeGrid();
        this.controller = controller;
        setGameStatus();
        startThread();
        this.X_Cell = new Cell();
        X_Cell.setCellStatus(CellState.X);
        this.O_Cell = new Cell();
        O_Cell.setCellStatus(CellState.O);
    }

    public void humanTakeTurn(Coordinates coordinates) {
        if (GameStatus.HUMAN_PLAYER_X_TURN == gameStatus
                || GameStatus.HUMAN_PLAYER_O_TURN == gameStatus) {
            boolean successfulTurn = ticTacToeGrid.setCell(coordinates.getRow(), coordinates.getColumn()
                    , CellState.valueOf(playerTurn.toString()));
            if (successfulTurn) {
                controller.placeMoveOnCellView(coordinates, playerTurn);
                changePlayerTurn();
                setGameStatus();
            }
        }
    }

    public void robotTakeTurn() {
        if (GameStatus.ROBOT_PLAYER_X_TURN == gameStatus
                || GameStatus.ROBOT_PLAYER_O_TURN == gameStatus) {
            Coordinates coordinates = ticTacToeGrid.findBestMove();
            ticTacToeGrid.setCell(coordinates.getRow(), coordinates.getColumn(), CellState.valueOf(playerTurn.toString()));
            controller.placeMoveOnCellView(coordinates, playerTurn);
            //setGameStatus();
            changePlayerTurn();
            setGameStatus();
            controller.setViewLabelStatus(gameStatus.getValue());
        }
    }

    private void startThread() {
        Thread t1 = new Thread(() -> {
            do {
                if (GameStatus.ROBOT_PLAYER_X_TURN == gameStatus
                        || GameStatus.ROBOT_PLAYER_O_TURN == gameStatus) {
                    robotTakeTurn();
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while(GameStatus.GAME_NOT_STARTED == gameStatus
                    || GameStatus.GAME_IN_PROGRESS == gameStatus
                    || GameStatus.HUMAN_PLAYER_X_TURN == gameStatus
                    || GameStatus.HUMAN_PLAYER_O_TURN == gameStatus
                    || GameStatus.ROBOT_PLAYER_X_TURN == gameStatus
                    || GameStatus.ROBOT_PLAYER_O_TURN == gameStatus);
        });
        t1.start();
    }

    private void changePlayerTurn() {
        playerTurn = playerTurn == PlayerTurn.X ? PlayerTurn.O : PlayerTurn.X;
    }

    private void setGameStatus() {
        if (ticTacToeGrid.hasThreeInRow(X_Cell)
                && player1Type == PlayerType.HUMAN) {
            gameStatus = GameStatus.HUMAN_PLAYER_X_WINS;
        } else if (ticTacToeGrid.hasThreeInRow(O_Cell)
                && player2Type == PlayerType.HUMAN) {
            gameStatus = GameStatus.HUMAN_PLAYER_O_WINS;
        } else if (ticTacToeGrid.hasThreeInRow(X_Cell)
                && player1Type == PlayerType.ROBOT) {
            gameStatus = GameStatus.ROBOT_PLAYER_X_WINS;
        } else if (ticTacToeGrid.hasThreeInRow(O_Cell)
                && player2Type == PlayerType.ROBOT) {
            gameStatus = GameStatus.ROBOT_PLAYER_O_WINS;
        } else if (!ticTacToeGrid.hasEmptyCell()) {
            gameStatus = GameStatus.DRAW;
        } else if (player1Type == PlayerType.HUMAN
                && playerTurn == PlayerTurn.X) {
            gameStatus = GameStatus.HUMAN_PLAYER_X_TURN;
        } else if (player2Type == PlayerType.HUMAN
                && playerTurn == PlayerTurn.O) {
            gameStatus = GameStatus.HUMAN_PLAYER_O_TURN;
        } else if (player1Type == PlayerType.ROBOT
                && playerTurn == PlayerTurn.X) {
            gameStatus = GameStatus.ROBOT_PLAYER_X_TURN;
        } else if (player2Type == PlayerType.ROBOT
                && playerTurn == PlayerTurn.O) {
            gameStatus = GameStatus.ROBOT_PLAYER_O_TURN;
        }
    }

    public PlayerTurn getPlayerTurn() {
        return playerTurn;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }
}
