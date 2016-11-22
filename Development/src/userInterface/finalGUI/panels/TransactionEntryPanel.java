package userInterface.finalGUI.panels;

import app.constants.Constants;

import javax.swing.*;
import java.util.Map;

/**
 * Individual transaction entry panel.
 * This panel is a representation of a previous transaction.
 */
class TransactionEntryPanel extends BasePanel {

    /**Label to display the timestamp.*/
    private final JLabel timestampLabel = new JLabel();
    /**Label to display the type of trade.*/
    private final JLabel tradeTypeLabel = new JLabel();
    /**Label to display the item that was traded.*/
    private final JLabel tradeItemLabel = new JLabel();
    /**Label to display the number of shares traded.*/
    private final JLabel shareQtyLabel = new JLabel();
    /**Label to display the total value of the transaction.*/
    private final JLabel transactionCostLabel = new JLabel();
    /**Label to display the new balance after the transaction.*/
    private final JLabel newBalanceLabel = new JLabel();

    /**
     * Create a new stock entry panel.
     */
    TransactionEntryPanel(){
        super();
        buildPanel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Add timestamp label*/
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(timestampLabel);

        /*Add trade type label*/
        constraints.gridx++;
        addComponent(tradeTypeLabel);

        /*Add traded item label*/
        constraints.gridx++;
        addComponent(tradeItemLabel);

        /*Add share qty label*/
        constraints.gridx++;
        addComponent(shareQtyLabel);

        /*Add transaction cost label*/
        constraints.gridx++;
        addComponent(transactionCostLabel);

        /*Add new balance label*/
        constraints.gridx++;
        addComponent(newBalanceLabel);
    }

    /**
     * Update the transaction labels from a map of transaction data.
     * @param transaction map of data associated with transaction.
     */
    void updateTransactionLabels(Map<String,String> transaction){
        /*Assign each entry to the specified labels*/
        String timestamp = transaction.get(Constants.TIMESTAMP_LABEL_KEY);
        String tradeType = transaction.get(Constants.TRADE_TYPE_LABEL_KEY);
        String tradeItem = transaction.get(Constants.TRADE_ITEM_LABEL_KEY);
        String shareQty = transaction.get(Constants.SHARE_QTY_LABEL_KEY);
        String transactionCost = transaction.get(Constants.TRANSACTION_COST_LABEL_KEY);
        String newBalance = transaction.get(Constants.NEW_BALANCE_LABEL_KEY);
        if(timestamp == null ||
                tradeType == null ||
                tradeItem == null ||
                shareQty == null ||
                transactionCost == null ||
                newBalance == null){
            return;
        }
        timestampLabel.setText(timestamp);
        tradeTypeLabel.setText(tradeType);
        tradeItemLabel.setText(tradeItem);
        shareQtyLabel.setText(shareQty);
        transactionCostLabel.setText(transactionCost);
        newBalanceLabel.setText(newBalance);
    }
}
