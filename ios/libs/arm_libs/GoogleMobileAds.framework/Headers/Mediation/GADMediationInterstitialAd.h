//
//  GADMediationInterstitialAd.h
//  Google Mobile Ads SDK
//
//  Copyright 2018 Google LLC. All rights reserved.
//

#import "GADMediationAd.h"
#import "GADMediationAdConfiguration.h"
#import "GADMediationAdEventDelegate.h"
#import <UIKit/UIKit.h>

/// Rendered interstitial ad.
@protocol GADMediationInterstitialAd <GADMediationAd>

/// Presents the receiver from the view controller.
- (void)presentFromViewController:(nonnull UIViewController *)viewController;

@end

/// Interstitial ad configuration.
@interface GADMediationInterstitialAdConfiguration : GADMediationAdConfiguration
@end
