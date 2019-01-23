package com.example.greentea.fteam.contribution


//class NewCompFragment : Fragment() {
//
//    private lateinit var mFirebaseFirestore: FirebaseFirestore
//    private lateinit var dialog:ProgressDialog
//    private var mCompID:String = ""
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_new_comp, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        /**
//         * 「新競技名」欄にテキストを入力したときのリスナー
//         **/
//        newCompName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                makeCompButton.isEnabled = !(newCompName.text.isEmpty() || newCompRule.text.isEmpty())
//            }
//        })
//
//        /**
//         * 「競技のルール」欄にテキストを入力したときのリスナー
//         **/
//        newCompRule.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun afterTextChanged(p0: Editable?) {}
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                makeCompButton.isEnabled = !(newCompName.text.isEmpty() || newCompRule.text.isEmpty())
//            }
//        })
//
//        /**
//         * 「新しく競技を作成」ボタンを押したときの処理
//         * 上記2つのEditTextが埋まっていないと押せない
//         **/
//        makeCompButton.setOnClickListener {
//            dialog = ProgressDialog(context)
//            dialog.setTitle("登録処理中")
//            dialog.show()
//            makeCompButton.isEnabled = false
//            createComp()
//        }
//
//        /**
//         * カメラボタンをタップしたときの処理
//         **/
//        camera_cardView.setOnClickListener {
//            val intent = Intent(this@NewCompFragment.context, VideoActivity::class.java)
//            intent.putExtra(COMP_ID_KEY, mCompID)
//            startActivity(intent)
//        }
//
//    }
//
//    private fun createComp(){
//        mFirebaseFirestore = FirebaseFirestore.getInstance()
//
////        val timestamp = Timestamp(System.currentTimeMillis())
//
//        val tempData = CompetitionObject(newCompName.text.toString(), newCompRule.text.toString(), Date())
//        mFirebaseFirestore.collection("competition")
//                .add(tempData)
//                .addOnSuccessListener {
//                    dialog.dismiss()
//                    mCompID = it.id
//                    submitMessage.text = "競技が作成されました。\n 早速撮影しますか？"
//                    camera_cardView.visibility = View.VISIBLE
//                    newCompName.isEnabled = false
//                    newCompRule.isEnabled = false
//                }
//                .addOnFailureListener {
//                    dialog.dismiss()
//                    Toast.makeText(context, "競技作成に失敗しました。時間を空けてもう一度試してください", Toast.LENGTH_SHORT).show()
//                }
//    }
//}
