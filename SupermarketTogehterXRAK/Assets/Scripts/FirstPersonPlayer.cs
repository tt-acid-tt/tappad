using UnityEngine;
using Unity.Netcode;
namespace SupermarketTogehterXRAK {
 [RequireComponent(typeof(CharacterController))]
 public sealed class FirstPersonPlayer : NetworkBehaviour {
  public float walkSpeed=5.2f, sprintSpeed=8f, gravity=-22f, jumpHeight=1.1f, sensitivity=2f;
  CharacterController cc; Transform pivot; Camera cam; float pitch, vy, stamina=100; bool solo;
  void Awake(){cc=GetComponent<CharacterController>();}
  public void EnableSolo(){solo=true;SetupCamera();}
  public override void OnNetworkSpawn(){if(IsOwner)SetupCamera();else {var c=GetComponentInChildren<Camera>();if(c)c.enabled=false;}}
  void SetupCamera(){
   if(cam)return;
   var p=new GameObject("CameraPivot");p.transform.SetParent(transform,false);p.transform.localPosition=new Vector3(0,1.62f,0);pivot=p.transform;
   var c=new GameObject("FirstPersonCamera");c.transform.SetParent(pivot,false);cam=c.AddComponent<Camera>();cam.fieldOfView=78;cam.nearClipPlane=.04f;cam.farClipPlane=250;c.AddComponent<AudioListener>();
   Cursor.lockState=CursorLockMode.Locked;Cursor.visible=false;
  }
  void Update(){if(!solo&&!IsOwner)return;if(!cam)return;
   if(Input.GetKeyDown(KeyCode.Escape)){Cursor.lockState=CursorLockMode.None;Cursor.visible=true;}
   if(Cursor.lockState!=CursorLockMode.Locked){if(Input.GetMouseButtonDown(0)){Cursor.lockState=CursorLockMode.Locked;Cursor.visible=false;}return;}
   transform.Rotate(0,Input.GetAxisRaw("Mouse X")*sensitivity,0);pitch=Mathf.Clamp(pitch-Input.GetAxisRaw("Mouse Y")*sensitivity,-82,82);pivot.localRotation=Quaternion.Euler(pitch,0,0);
   bool run=Input.GetKey(KeyCode.LeftShift)&&stamina>0;stamina=Mathf.Clamp(stamina+(run?-26:18)*Time.deltaTime,0,100);
   Vector3 move=(transform.right*Input.GetAxisRaw("Horizontal")+transform.forward*Input.GetAxisRaw("Vertical")).normalized;
   cc.Move(move*(run?sprintSpeed:walkSpeed)*Time.deltaTime);if(cc.isGrounded&&vy<0)vy=-2;
   if(cc.isGrounded&&Input.GetButtonDown("Jump"))vy=Mathf.Sqrt(jumpHeight*-2*gravity);vy+=gravity*Time.deltaTime;cc.Move(Vector3.up*vy*Time.deltaTime);
   if(Input.GetKeyDown(KeyCode.E)&&Physics.Raycast(cam.transform.position,cam.transform.forward,out var hit,3.3f)){var a=hit.collider.GetComponentInParent<StoreInteractable>();if(a)a.Use(this);}
  }
 }
}