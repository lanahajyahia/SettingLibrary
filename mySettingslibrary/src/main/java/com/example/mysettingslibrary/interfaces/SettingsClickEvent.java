package com.example.mysettingslibrary.interfaces;


import androidx.preference.Preference;

public class SettingsClickEvent
{
	private Preference preferenceClicked;

	/**
	 * constructor for ever Preference that be clicked
	 * @param preferenceClicked
	 */
	public SettingsClickEvent(Preference preferenceClicked)
	{
		this.preferenceClicked = preferenceClicked;
	}

	/**
	 *
	 * @return the {@link Preference} that was clicked
	 */
	public Preference getClickedSettingsObj()
	{
		return preferenceClicked;
	}
}