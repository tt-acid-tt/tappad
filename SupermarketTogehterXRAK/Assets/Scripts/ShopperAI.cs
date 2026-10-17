using UnityEngine;
namespace SupermarketTogehterXRAK {
 public sealed class ShopperAI:MonoBehaviour {
  Vector3 target;float pause;int visits;float seed;
  public void Init(Vector3 pos,Material shirt,Material skin){transform.position=pos;seed=Random.value*10;
   Part(PrimitiveType.Capsule,"Jacket",new Vector3(0,.92f,0),new Vector3(.48f,.68f,.38f),shirt);
   Part(PrimitiveType.Sphere,"Head",new Vector3(0,1.78f,0),Vector3.one*.34f,skin);
   Part(PrimitiveType.Capsule,"Trousers",new Vector3(0,.28f,0),new Vector3(.4f,.32f,.3f),shirt);
   target=NextTarget();
  }
  void Part(PrimitiveType type,string n,Vector3 pos,Vector3 scale,Material mat){var o=GameObject.CreatePrimitive(type);o.name=n;o.transform.SetParent(transform,false);o.transform.localPosition=pos;o.transform.localScale=scale;o.GetComponent<Renderer>().material=mat;Destroy(o.GetComponent<Collider>());}
  Vector3 NextTarget()=>new Vector3(Random.Range(-8,8),0,Random.Range(-4,9));
  void Update(){if(pause>0){pause-=Time.deltaTime;return;}var d=target-transform.position;d.y=0;if(d.magnitude>.25f){transform.position+=d.normalized*(1.2f+Mathf.Sin(Time.time*3+seed)*.05f)*Time.deltaTime;transform.rotation=Quaternion.Slerp(transform.rotation,Quaternion.LookRotation(d),Time.deltaTime*4);}else{pause=Random.Range(1.5f,4);target=NextTarget();if(++visits%2==0&&SupermarketGame.Instance)SupermarketGame.Instance.CustomerPurchase();}}
 }
}