package lv.ctco.javaschool.game.control;

import lv.ctco.javaschool.auth.entity.domain.User;
import lv.ctco.javaschool.game.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class GameStore {
    @PersistenceContext
    private EntityManager em;

    public Optional<Game> getIncompleteGame() {
        return em.createQuery(
                "select g " + "from Game g " + "where g.status = :status", Game.class)
                .setParameter("status", GameStatus.INCOMPLETE)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
    public Optional<Game> getStartedGameFor(User user, GameStatus status) {
        return em.createQuery(
                "select g " + "from Game g " + "where g.status = :status " + "  and (g.player1 = :user " +
                        "   or g.player2 = :user)", Game.class)
                .setParameter("status", status)
                .setParameter("user", user)
                .getResultStream()
                .findFirst();
    }
    public List<TopTable> getTopTable() {
        return em.createQuery(
                "select t " + "from TopTable t ", TopTable.class)
                .setMaxResults(10)
                .getResultList();
    }
    public Optional<Game> getLastGameFor(User currentUser) {
        return em.createQuery(
                "select g " +
                        "from Game g " +
                        "  where (g.player1 = :user " +
                        "   or g.player2 = :user) " +
                        "order by g.id desc ", Game.class)
                .setParameter("user", currentUser)
                .setMaxResults(1)
                .getResultStream()
                .findFirst();
    }
    public Optional<Game> getOpenGameFor(User user) {
        return em.createQuery(
                "select g " +
                        "from Game g " +
                        "where g.status <> :status " +
                        "  and (g.player1 = :user " +
                        "   or g.player2 = :user)", Game.class)
                .setParameter("status", GameStatus.FINISHED)
                .setParameter("user", user)
                .getResultStream()
                .findFirst();
    }
    public Optional<Cell> getCellState(Game game, User player, String address, boolean targetArea){
        Optional<Cell> cell = em.createQuery(
                "select c from Cell c " +
                        "where c.game = :game " +
                        "  and c.user = :user " +
                        "  and c.targetArea = :target " +
                        "  and c.address = :address", Cell.class)
                .setParameter("game", game)
                .setParameter("user", player)
                .setParameter("target", targetArea)
                .setParameter("address", address)
                .getResultStream()
                .findFirst();
        return cell;
    }
    public void setCellState(Game game, User player, String address, boolean targetArea, CellState state) {
        Optional<Cell> cell = getCellState(game,player,address,targetArea);
        if (cell.isPresent()) {
            cell.get().setState(state);
        } else {
            Cell newCell = new Cell();
            newCell.setGame(game);
            newCell.setUser(player);
            newCell.setAddress(address);
            newCell.setTargetArea(targetArea);
            newCell.setState(state);
            em.persist(newCell);
        }
    }
    public void setWinner(User user, int shots) {
            TopTable winner = new TopTable();
            winner.setWinName(user);
            winner.setShots(shots);
            em.persist(winner);
    }
    public void setShips(Game game, User player, boolean targetArea, List<String> ships) {
        clearField(game, player, targetArea);
        ships.stream()
                .map(addr -> {
                    Cell c = new Cell();
                    c.setGame(game);
                    c.setAddress(addr);
                    c.setTargetArea(targetArea);
                    c.setUser(player);
                    c.setState(CellState.SHIP);
                    return c;
                }).forEach(c -> em.persist(c));
    }
    private void clearField(Game game, User player, boolean targetArea) {
        List<Cell> cells = em.createQuery(
                "select c " +
                        "from Cell c " +
                        "where c.game = :game " +
                        "  and c.user = :user " +
                        "  and c.targetArea = :target", Cell.class)
                .setParameter("game", game)
                .setParameter("user", player)
                .setParameter("target", targetArea)
                .getResultList();
        cells.forEach(c -> em.remove(c));
    }
    public List<Cell> getCells(Game game, User player) {
        return em.createQuery(
                "select c " +
                        "from Cell c " +
                        "where c.game = :game " +
                        "  and c.user = :user ", Cell.class)
                .setParameter("game", game)
                .setParameter("user", player)
                .getResultList();
    }
    public boolean hasShip(User player, Game game) {
        List<Cell> cells = getCells(game, player);
        for (Cell c : cells) {
            if (c.getState() == CellState.SHIP && !c.isTargetArea()) {
                return true;
            }
        }
        return false;
    }
}