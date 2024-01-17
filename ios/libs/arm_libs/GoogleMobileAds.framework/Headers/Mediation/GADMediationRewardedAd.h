//
//  GADMediationRewardedAd.h
//  Google Mobile Ads SDK
//
//  Copyright 2018 Google LLC. All rights reserved.
//

#import "GADMediationAd.h"
#import "GADMediationAdConfiguration.h"
#import "GADMediationAdEventDelegate.h"
#import <UIKit/UIKit.h>

/// Rendered rewarded ad.
@protocol GADMediationRewardedAd <GADMediationAd>
- (void)presentFromViewController:(nonnull UIViewController *)viewController;
@end

/// Rewarded ad configuration.
@interface GADMediationRewardedAdConfiguration : GADMediationAdConfiguration
@end
