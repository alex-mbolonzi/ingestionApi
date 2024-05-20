import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IngestionApiTestModule } from '../../../test.module';
import { TechnicalDetailsDetailComponent } from 'app/entities/technical-details/technical-details-detail.component';
import { TechnicalDetails } from 'app/shared/model/technical-details.model';

describe('Component Tests', () => {
  describe('TechnicalDetails Management Detail Component', () => {
    let comp: TechnicalDetailsDetailComponent;
    let fixture: ComponentFixture<TechnicalDetailsDetailComponent>;
    const route = { data: of({ technicalDetails: new TechnicalDetails(123) }) } as any as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IngestionApiTestModule],
        declarations: [TechnicalDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TechnicalDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TechnicalDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.technicalDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
