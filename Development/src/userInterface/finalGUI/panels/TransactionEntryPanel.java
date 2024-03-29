package userInterface.finalGUI.panels;

import app.constants.Constants;

import javax.swing.*;
import java.awt.*;
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

    private final Object[] entries = new Object[6];

    /**
     * Create a new stock entry panel.
     */
    TransactionEntryPanel(){
        setLayout(new FlowLayout());
        buildPanel();
    }

    Object[] getEntries(){
        return entries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    void buildPanel() {
        /*Add timestamp label*/
        add(timestampLabel);

        /*Add trade type label*/
        add(tradeTypeLabel);

        /*Add traded item label*/
        add(tradeItemLabel);

        /*Add share qty label*/
        add(shareQtyLabel);

        /*Add transaction cost label*/
        add(transactionCostLabel);

        /*Add new balance label*/
        add(newBalanceLabel);
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
        entries[0] = timestamp;
        entries[1] = tradeType;
        entries[2] = tradeItem;
        entries[3] = shareQty;
        entries[4] = transactionCost;
        entries[5] = newBalance;

        timestampLabel.setText(timestamp);
        tradeTypeLabel.setText(tradeType);
        tradeItemLabel.setText(tradeItem);
        shareQtyLabel.setText(shareQty);
        transactionCostLabel.setText(transactionCost);
        newBalanceLabel.setText(newBalance);
    }
}
