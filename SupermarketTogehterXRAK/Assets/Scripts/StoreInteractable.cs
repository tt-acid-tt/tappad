using UnityEngine;
namespace SupermarketTogehterXRAK {
 public sealed class StoreInteractable:MonoBehaviour {
  public enum ActionType{Restock,Checkout,Order,Upgrade,Box}
  public ActionType actionType; public string displayName="Interact"; public int stock=12;
  public void Use(FirstPersonPlayer player){var g=SupermarketGame.Instance;if(!g)return;switch(actionType){
   case ActionType.Restock: if(stock>0&&g.money>=5&&g.shelfStock<100){g.money-=5;g.shelfStock=Mathf.Min(100,g.shelfStock+8);stock--;g.Message("Shelf restocked  +8 units");}else g.Message("Need money or more shelf space.");break;
   case ActionType.Checkout:g.Checkout();break;
   case ActionType.Order:if(g.money>=80){g.money-=80;g.shelfStock=Mathf.Min(100,g.shelfStock+35);g.Message("Delivery arrived: +35 units");}else g.Message("Delivery costs $80.");break;
   case ActionType.Upgrade:int cost=g.storeLevel*250;if(g.money>=cost){g.money-=cost;g.storeLevel++;g.reputation++;g.Message("Store upgraded!");}else g.Message("Upgrade costs $"+cost);break;
   case ActionType.Box:g.shelfStock=Mathf.Min(100,g.shelfStock+4);g.Message("Collected a carton: +4 stock");gameObject.SetActive(false);break;
  }}
 }
}