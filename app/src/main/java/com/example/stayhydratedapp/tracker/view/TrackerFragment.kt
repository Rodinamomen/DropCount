package com.example.stayhydratedapp.tracker.view

import android.app.Dialog
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.stayhydratedapp.NotificationManger
import com.example.stayhydratedapp.R
import com.example.stayhydratedapp.database.LocalDatabaseImp
import com.example.stayhydratedapp.database.Record
import com.example.stayhydratedapp.databinding.FragmentTrackerBinding
import com.example.stayhydratedapp.tracker.adapters.RecordsAdapter
import com.example.stayhydratedapp.tracker.repo.TrackerRepoImp
import com.example.stayhydratedapp.tracker.viewmodel.TrackerViewModel
import com.example.stayhydratedapp.tracker.viewmodel.TrackerViewModelFactory
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate
import java.util.concurrent.TimeUnit


class TrackerFragment : Fragment() {
    private lateinit var settingsDialog: View
    private lateinit var mySettingsDialog: Dialog
    private lateinit var dialog: View
    private lateinit var myDialog: Dialog
   private val dailyWaterGoal = 2430
    private lateinit var viewmodel : TrackerViewModel
companion object{
    val KEY = "total_pref"
}

    private lateinit var binding: FragmentTrackerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentTrackerBinding.inflate(inflater, container, false)
        val toolBar = binding.topAppBar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolBar)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        gettingViewModelReady(requireContext())
        viewmodel.getRecords()
        val sharedPreferences = createSharedPreferences()
    settingsDialog = layoutInflater.inflate(R.layout.settings_dialog, null)
        mySettingsDialog = Dialog(requireContext())
         dialog = layoutInflater.inflate(R.layout.cups_dialog, null)
         myDialog = Dialog(requireContext())
        val cup100 = dialog.findViewById<ImageView>(R.id.iv_100ml)
        val cup300 = dialog.findViewById<ImageView>(R.id.iv_300ml)
        val cup400 = dialog.findViewById<ImageView>(R.id.iv_400ml)
        val darkModeSwitch = settingsDialog.findViewById<Switch>(R.id.switch_dark_mode)
        getSavedTotal(sharedPreferences)?.toInt()?.let { updateProgressBar(it) }
        var currentDrawableResId: Int = R.drawable.ic_cup100
        viewmodel.recordInserted.observe(requireActivity()){
            if(it == true){
               viewmodel.getRecords()
            }
        }
        viewmodel.records.observe(requireActivity()){
            if(it.isEmpty()){
                binding.rvRecords.visibility = View.GONE
                binding.ivNoRecord.visibility= View.VISIBLE
                binding.tvNoRecord.visibility= View.VISIBLE
                binding.tvPressPlus.visibility=View.VISIBLE

            }else{
                binding.rvRecords.visibility= View.VISIBLE
                binding.ivNoRecord.visibility= View.GONE
                binding.tvNoRecord.visibility= View.GONE
                binding.tvPressPlus.visibility=View.GONE
            }
            val adapter = RecordsAdapter(it)
            binding.rvRecords.adapter = adapter
        }
        binding.rvRecords.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        binding.ivCup.setOnClickListener {
            myDialog.setContentView(dialog)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            cup100.setOnClickListener {
                binding.ivWaterCup.setImageResource(R.drawable.ic_cup100)
                currentDrawableResId = R.drawable.ic_cup100
                myDialog.dismiss()
            }
            cup300.setOnClickListener {
                binding.ivWaterCup.setImageResource(R.drawable.ic_cup300)
                currentDrawableResId = R.drawable.ic_cup300
                myDialog.dismiss()
            }
            cup400.setOnClickListener {
                binding.ivWaterCup.setImageResource(R.drawable.ic_cup400)
                currentDrawableResId = R.drawable.ic_cup400
                myDialog.dismiss()
            }
        }
        binding.btnAddWater.setOnClickListener {
            var currentTotal = binding.tvTotal.text.toString().toInt()
            when (currentDrawableResId) {
                R.drawable.ic_cup100 -> {
                    val intakeAmount = 100
                    binding.tvTotal.text = (currentTotal + intakeAmount).toString()
                    currentTotal += intakeAmount
                    setNewValue(sharedPreferences,binding.tvTotal.text .toString())
                    updateProgressBar(currentTotal)
                    insertNewRecord(intakeAmount)
                }

                R.drawable.ic_cup300 -> {
                    val intakeAmount = 300
                    binding.tvTotal.text = (currentTotal + intakeAmount).toString()
                    currentTotal += 300
                    setNewValue(sharedPreferences,binding.tvTotal.text .toString())
                    updateProgressBar(currentTotal)
                    insertNewRecord(intakeAmount)
                }
                R.drawable.ic_cup400 -> {
                    val intakeAmount = 400
                    binding.tvTotal.text = (currentTotal + intakeAmount).toString()
                    currentTotal += intakeAmount
                    setNewValue(sharedPreferences,binding.tvTotal.text .toString())
                    updateProgressBar(currentTotal)
                    insertNewRecord(intakeAmount)
                }
            }
            if(currentTotal>=2430){
                showAnimation()
            }
        }
        binding.switchWaterReminder2.isChecked= getNotificationSwitchState()
        darkModeSwitch.isChecked = getDarkModeSwitchState()
        darkModeSwitch.setOnCheckedChangeListener{
                _, isChecked ->
            if(isChecked){
                saveDarkModeSwitchState(true)

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mySettingsDialog.dismiss()

            }else{
                saveDarkModeSwitchState(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mySettingsDialog.dismiss()
            }
        }
        binding.switchWaterReminder2.setOnCheckedChangeListener {
                _, isChecked ->
            if(isChecked){
                saveNotificationSwitchState(true)
                startPeriodicNotificationWorker()
            }else{
                saveNotificationSwitchState(false)
                Toast.makeText(requireContext(), "You disabled notifications", Toast.LENGTH_SHORT).show()
                cancelPeriodicNotificationWorker()
            }
        }
    }
    private fun createSharedPreferences(): SharedPreferences{
        val sharedPreferences = requireContext().getSharedPreferences(KEY, MODE_PRIVATE)
        return sharedPreferences
    }
    private fun getSavedTotal(sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString("total","0")
    }
    private fun setNewValue(sharedPreferences: SharedPreferences, total:String){
        val editor = sharedPreferences.edit()
        editor.putString("total", total).apply()
    }
    private fun insertNewRecord(recordIntakeAmount:Int){
        val newRecord = Record(recordDate = formatDate(), recordIntakeAmount = recordIntakeAmount)
        viewmodel.insertRecord(newRecord)
    }
    private fun formatDate(): String{
        return SimpleDateFormat("h:mm a MMM dd", Locale.getDefault()).format(Date())
    }
    private fun startPeriodicNotificationWorker(){
        val constraints= Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build()
        val workerRequest = PeriodicWorkRequest.Builder(NotificationManger::class.java,60, TimeUnit.MINUTES).setConstraints(constraints).addTag("Notification_Tag")
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork("Notification_Tag",
            ExistingPeriodicWorkPolicy.KEEP,workerRequest)
    }
    private fun cancelPeriodicNotificationWorker(){
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag("Notification_Tag")
    }
    private fun saveNotificationSwitchState(isEnabled: Boolean) {
        val sharedPreferences = requireContext().getSharedPreferences("notification_switch_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("switch_state", isEnabled)
        editor.apply()
    }
    private fun getNotificationSwitchState(): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("notification_switch_prefs",  MODE_PRIVATE)
        return sharedPreferences.getBoolean("notification_switch_state", false)
    }
    private fun getDarkModeSwitchState(): Boolean {
        val sharedPreferences = requireContext().getSharedPreferences("dark_mode_switch_prefs",  MODE_PRIVATE)
        return sharedPreferences.getBoolean("dark_mode_switch_state", false)
    }
    private fun saveDarkModeSwitchState(isEnabled: Boolean) {
        val sharedPreferences = requireContext().getSharedPreferences("dark_mode_switch_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode_switch_state", isEnabled)
        editor.apply()
    }

    private fun showAnimation() {
        val colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.md_theme_primary),
            ContextCompat.getColor(requireContext(), R.color.md_theme_primaryContainer),
            ContextCompat.getColor(requireContext(), R.color.md_theme_secondary)
        )
        binding.konfettiView.start(party =  Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = colors,
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        ))
    }
    private fun updateProgressBar(currentTotal: Int) {
        val progressPercentage = (currentTotal.toFloat() / dailyWaterGoal) * 100
        binding.pbTotal.setProgressWithAnimation(progressPercentage, 1000) // 1000 ms for smooth animation
    }
    private fun gettingViewModelReady(context: Context){
        val trackerViewModelFactory = TrackerViewModelFactory(TrackerRepoImp(LocalDatabaseImp(context)))
        viewmodel = ViewModelProvider(this, trackerViewModelFactory).get(TrackerViewModel::class.java)
    }

   private fun getLastOpenedDate(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("DatePreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("lastOpenedDate", null)
    }
   private fun saveCurrentDate(context: Context) {
        val sharedPreferences = context.getSharedPreferences("DatePreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentDate = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
        editor.putString("lastOpenedDate", currentDate)
        editor.apply()
    }
    private fun resetTotal(sharedPreferences: SharedPreferences){
        if (getLastOpenedDate(requireContext()) != SimpleDateFormat("dd", Locale.getDefault()).format(Date())) {
            binding.tvTotal.text="0"
            updateProgressBar(0)
            saveCurrentDate(requireContext())
        }else{
            binding.tvTotal.text = getSavedTotal(sharedPreferences)
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = createSharedPreferences()
        resetTotal(sharedPreferences)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_settings -> {
                mySettingsDialog.setContentView(settingsDialog)
                mySettingsDialog.setCancelable(true)
                mySettingsDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mySettingsDialog.show()}
            else -> Log.d("menu", "onOptionsItemSelected:noting  ")
        }

        return super.onOptionsItemSelected(item)
    }
}