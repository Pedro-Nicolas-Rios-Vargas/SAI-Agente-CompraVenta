package agents;

import behaviours.RequestPerformer;

import gui.BookBuyerGui;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {

  private String bookTitle;
  private AID[] sellerAgents;
  private int ticker_timer = 10000;
  private BookBuyerAgent this_agent = this;

  private BookBuyerGui gui;
  
  protected void setup() {
    System.out.println("Buyer agent " + getAID().getName() + " is ready");

    gui = new BookBuyerGui(this);
    gui.showGui();
    
  }
  
  protected void takeDown() {

    gui.dispose();

    System.out.printf("\nBuyer agent \"%s\" terminating\n",
            getAID().getName());

  }
  
  public AID[] getSellerAgents() {
    return sellerAgents;
  }

  public BookBuyerGui getGUI() {
      return gui;
  }
  
  public void updateBookTitle(String bookTitle) {
      this.bookTitle = bookTitle;

      addBehaviour(new TickerBehaviour(this, ticker_timer) {
        protected void onTick() {
          System.out.println("Trying to buy " + bookTitle);
          
          DFAgentDescription template = new DFAgentDescription();
          ServiceDescription sd = new ServiceDescription();
          sd.setType("book-selling");
          template.addServices(sd);
          
          try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Found the following seller agents:");
            sellerAgents = new AID[result.length];
            for(int i = 0; i < result.length; i++) {
              sellerAgents[i] = result[i].getName();
              System.out.println(sellerAgents[i].getName());
            }
            
          } catch(FIPAException fe) {
            fe.printStackTrace();
          }
          
          myAgent.addBehaviour(new RequestPerformer(this_agent));
        }
      });
  }

  public String getBookTitle() {
    return bookTitle;
  }

}
